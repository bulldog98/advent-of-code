package year2024.day24

sealed interface ComputationTree {
    val variableName: String
    fun complete(lookup: Map<String, ComputationTree>): ComputationTree
    fun getUsedVariableNames(): List<String>
    fun rewireOutput(oldVariableName: String, newVariableName: String): ComputationTree
    fun congruentTo(computationTree: ComputationTree): Boolean
    fun computeValue(lookup: (String) -> Boolean): Boolean

    data class InputVariable(override val variableName: String) : ComputationTree {
        override fun complete(lookup: Map<String, ComputationTree>): ComputationTree = this
        override fun getUsedVariableNames(): List<String> = listOf(variableName)
        override fun rewireOutput(oldVariableName: String, newVariableName: String): ComputationTree = copy(
            variableName = when (variableName) {
                oldVariableName -> newVariableName
                newVariableName -> oldVariableName
                else -> variableName
            }
        )

        override fun congruentTo(computationTree: ComputationTree): Boolean = this == computationTree
        override fun computeValue(lookup: (String) -> Boolean): Boolean = lookup(variableName)
    }

    sealed interface GateComputation : ComputationTree {
        val left: ComputationTree
        val right: ComputationTree
        val computation: (Boolean, Boolean) -> Boolean
        fun overwrite(left: ComputationTree, right: ComputationTree): ComputationTree
        override fun complete(lookup: Map<String, ComputationTree>): ComputationTree {
            val newLeft = when (left) {
                is InputVariable -> lookup[left.variableName] ?: error("could not lookup ${left.variableName}")
                else -> left.complete(lookup)
            }
            val newRight = when (right) {
                is InputVariable -> lookup[right.variableName] ?: error("could not lookup ${right.variableName}")
                else -> right.complete(lookup)
            }
            if (left == newLeft && right == newRight) return this
            return overwrite(left = newLeft, right = newRight).complete(lookup)
        }

        override fun getUsedVariableNames(): List<String> =
            left.getUsedVariableNames() + variableName + right.getUsedVariableNames()

        override fun computeValue(lookup: (String) -> Boolean): Boolean = computation(
            left.computeValue(lookup),
            right.computeValue(lookup)
        )
    }

    data class AndGateComputation(
        override val variableName: String,
        override val left: ComputationTree,
        override val right: ComputationTree
    ) : GateComputation {
        override val computation: (Boolean, Boolean) -> Boolean = Boolean::and
        override fun overwrite(
            left: ComputationTree,
            right: ComputationTree
        ): ComputationTree = copy(variableName = variableName, left = left, right = right)

        override fun rewireOutput(oldVariableName: String, newVariableName: String): ComputationTree = copy(
            variableName = when (variableName) {
                oldVariableName -> newVariableName
                newVariableName -> oldVariableName
                else -> variableName
            }
        )

        override fun congruentTo(computationTree: ComputationTree): Boolean =
            computationTree is AndGateComputation && (
                (left.congruentTo(computationTree.left) && right.congruentTo(computationTree.right)) ||
                    (right.congruentTo(computationTree.left) && left.congruentTo(computationTree.right))
                )
    }

    data class XorGateComputation(
        override val variableName: String,
        override val left: ComputationTree,
        override val right: ComputationTree
    ) : GateComputation {
        override val computation: (Boolean, Boolean) -> Boolean = Boolean::xor
        override fun overwrite(
            left: ComputationTree,
            right: ComputationTree
        ): ComputationTree = copy(left = left, right = right)

        override fun rewireOutput(oldVariableName: String, newVariableName: String): ComputationTree = copy(
            variableName = when (variableName) {
                oldVariableName -> newVariableName
                newVariableName -> oldVariableName
                else -> variableName
            }
        )

        override fun congruentTo(computationTree: ComputationTree): Boolean =
            computationTree is XorGateComputation && (
                (left.congruentTo(computationTree.left) && right.congruentTo(computationTree.right)) ||
                    (right.congruentTo(computationTree.left) && left.congruentTo(computationTree.right))
                )
    }

    data class OrGateComputation(
        override val variableName: String,
        override val left: ComputationTree,
        override val right: ComputationTree
    ) : GateComputation {
        override val computation: (Boolean, Boolean) -> Boolean = Boolean::or
        override fun overwrite(
            left: ComputationTree,
            right: ComputationTree
        ): ComputationTree = copy(left = left, right = right)

        override fun rewireOutput(oldVariableName: String, newVariableName: String): ComputationTree = copy(
            variableName = when (variableName) {
                oldVariableName -> newVariableName
                newVariableName -> oldVariableName
                else -> variableName
            }
        )

        override fun congruentTo(computationTree: ComputationTree): Boolean =
            computationTree is OrGateComputation && (
                (left.congruentTo(computationTree.left) && right.congruentTo(computationTree.right)) ||
                    (right.congruentTo(computationTree.left) && left.congruentTo(computationTree.right))
                )
    }

    companion object {
        fun pares(gate: String): ComputationTree {
            val (param1, param2, variable) = gate.split(" AND ", " XOR ", " OR ", " -> ")
            return when {
                "AND" in gate -> AndGateComputation(
                    variableName = variable,
                    left = InputVariable(param1),
                    right = InputVariable(param2)
                )

                "XOR" in gate -> XorGateComputation(
                    variableName = variable,
                    left = InputVariable(param1),
                    right = InputVariable(param2)
                )

                "OR" in gate -> OrGateComputation(
                    variableName = variable,
                    left = InputVariable(param1),
                    right = InputVariable(param2)
                )

                else -> error("could not parse $gate")
            }
        }
    }
}