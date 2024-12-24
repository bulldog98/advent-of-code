package year2024

import adventday.AdventDay
import adventday.InputRepresentation
import helper.pair.mapFirst
import helper.pair.mapSecond

object Day24 : AdventDay(2024, 24) {
    private sealed interface Gate {
        val variable: String
        val dependsOn: List<String>
        fun compute(variables: Map<String, Boolean>): Boolean

        data class XorGate(val param1: String, val param2: String, override val variable: String) : Gate {
            override val dependsOn: List<String>
                get() = listOf(param1, param2)

            override fun compute(variables: Map<String, Boolean>): Boolean =
                (variables[param1]!! xor variables[param2]!!)
        }

        data class OrGate(val param1: String, val param2: String, override val variable: String) : Gate {
            override val dependsOn: List<String>
                get() = listOf(param1, param2)

            override fun compute(variables: Map<String, Boolean>): Boolean =
                (variables[param1]!! || variables[param2]!!)
        }

        data class AndGate(val param1: String, val param2: String, override val variable: String) : Gate {
            override val dependsOn: List<String>
                get() = listOf(param1, param2)

            override fun compute(variables: Map<String, Boolean>): Boolean =
                (variables[param1]!! && variables[param2]!!)
        }

        companion object {
            fun parse(line: String): Gate {
                val (var1, var2, result) = line.split(" AND ", " XOR ", " OR ", " -> ")
                return when {
                    "AND" in line -> AndGate(var1, var2, result)
                    "XOR" in line -> XorGate(var1, var2, result)
                    "OR" in line -> OrGate(var1, var2, result)
                    else -> error("cannot parse gate from $line")
                }
            }
        }
    }

    private data class Wiring(
        val initialValues: Map<String, Boolean>,
        val gates: List<Gate>
    ) {
        fun computeValues(): String {
            val resultGatesInCorrectOrder = gates.filter { it.variable.startsWith('z') }
                .sortedBy { it.variable }
                .reversed()
            val variableResult = resultGatesInCorrectOrder.fold(this) { wireing, nextGate ->
                wireing.computeValue(nextGate.variable)
            }.initialValues
            return resultGatesInCorrectOrder.joinToString("") {
                val result = variableResult[it.variable]
                when (result) {
                    true -> "1"
                    false -> "0"
                    else -> error("variable not computed")
                }
            }
        }

        private fun computeValue(value: String): Wiring = when {
            value in initialValues.keys -> this
            gates.any { it.variable == value } -> {
                val gate = gates.first { it.variable == value }
                val wireing = gate
                    .dependsOn
                    .fold(this) { w, variable ->
                        w.computeValue(variable)
                    }
                wireing.copy(
                    initialValues = initialValues + (value to gate.compute(wireing.initialValues))
                )
            }

            else -> error("should not happen")
        }

        companion object {
            fun parse(input: InputRepresentation): Wiring {
                val (variables, gates) = input.asTwoBlocks()
                    .mapFirst { lines ->
                        buildMap {
                            lines.forEach { line ->
                                val (name, value) = line.split(": ")
                                put(
                                    name, when (value) {
                                        "1" -> true
                                        "0" -> false
                                        else -> error("cannot parse $value to Boolean")
                                    }
                                )
                            }
                        }
                    }
                    .mapSecond { lines -> lines.map { Gate.parse(it) } }
                return Wiring(variables, gates)
            }
        }
    }

    private sealed interface ComputationTree {
        val variableName: String
        fun complete(lookup: Map<String, ComputationTree>): ComputationTree
        fun getUsedVariableNames(): List<String>
        fun rewireOutput(oldVariableName: String, newVariableName: String): ComputationTree
        fun congurentTo(computationTree: ComputationTree): Boolean

        data class VariableNodeComputationTree(override val variableName: String) : ComputationTree {
            override fun complete(lookup: Map<String, ComputationTree>): ComputationTree = this
            override fun getUsedVariableNames(): List<String> = listOf(variableName)
            override fun rewireOutput(oldVariableName: String, newVariableName: String): ComputationTree = copy(
                variableName = when (variableName) {
                    oldVariableName -> newVariableName
                    newVariableName -> oldVariableName
                    else -> variableName
                }
            )

            override fun congurentTo(computationTree: ComputationTree): Boolean = this == computationTree
        }

        sealed interface FunctionComputationTree : ComputationTree {
            val left: ComputationTree
            val right: ComputationTree
            fun overwrite(left: ComputationTree, right: ComputationTree): ComputationTree
            override fun complete(lookup: Map<String, ComputationTree>): ComputationTree {
                val newLeft = when (left) {
                    is VariableNodeComputationTree -> lookup[left.variableName] ?: error("could not lookup ${left.variableName}")
                    else -> left.complete(lookup)
                }
                val newRight = when (right) {
                    is VariableNodeComputationTree -> lookup[right.variableName] ?: error("could not lookup ${right.variableName}")
                    else -> right.complete(lookup)
                }
                if (left == newLeft && right == newRight) return this
                return overwrite(left = newLeft, right = newRight).complete(lookup)
            }

            override fun getUsedVariableNames(): List<String> =
                left.getUsedVariableNames() + variableName + right.getUsedVariableNames()
        }

        data class AndVariableComputationTree(
            override val variableName: String,
            override val left: ComputationTree,
            override val right: ComputationTree
        ) : FunctionComputationTree {
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

            override fun congurentTo(computationTree: ComputationTree): Boolean =
                computationTree is AndVariableComputationTree && (
                    (left.congurentTo(computationTree.left) && right.congurentTo(computationTree.right)) ||
                        (right.congurentTo(computationTree.left) && left.congurentTo(computationTree.right))
                    )
        }

        data class XorVariableComputationTree(
            override val variableName: String,
            override val left: ComputationTree,
            override val right: ComputationTree
        ) : FunctionComputationTree {
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
            override fun congurentTo(computationTree: ComputationTree): Boolean =
                computationTree is XorVariableComputationTree && (
                    (left.congurentTo(computationTree.left) && right.congurentTo(computationTree.right)) ||
                        (right.congurentTo(computationTree.left) && left.congurentTo(computationTree.right))
                    )
        }

        data class OrVariableComputationTree(
            override val variableName: String,
            override val left: ComputationTree,
            override val right: ComputationTree
        ) : FunctionComputationTree {
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
            override fun congurentTo(computationTree: ComputationTree): Boolean =
                computationTree is OrVariableComputationTree && (
                    (left.congurentTo(computationTree.left) && right.congurentTo(computationTree.right)) ||
                        (right.congurentTo(computationTree.left) && left.congurentTo(computationTree.right))
                    )
        }

        companion object {
            fun fromGate(gate: Gate) = when (gate) {
                is Gate.AndGate -> AndVariableComputationTree(
                    variableName = gate.variable,
                    left = VariableNodeComputationTree(gate.param1),
                    right = VariableNodeComputationTree(gate.param2)
                )

                is Gate.XorGate -> XorVariableComputationTree(
                    variableName = gate.variable,
                    left = VariableNodeComputationTree(gate.param1),
                    right = VariableNodeComputationTree(gate.param2)
                )

                is Gate.OrGate -> OrVariableComputationTree(
                    variableName = gate.variable,
                    left = VariableNodeComputationTree(gate.param1),
                    right = VariableNodeComputationTree(gate.param2)
                )
            }
        }
    }

    override fun part1(input: InputRepresentation): Long = Wiring.parse(input)
        .computeValues()
        .toLong(2)

    override fun part2(input: InputRepresentation): String {
        val directComputationMap =
            input.asTwoBlocks().second.map { ComputationTree.fromGate(Gate.parse(it))
            }.associateBy {
                it.variableName
            }
        val computationTreeLookup =
            directComputationMap + (directComputationMap.keys.filter { it.startsWith('z') }).flatMap {
                listOf(
                    ComputationTree.VariableNodeComputationTree("x" + it.drop(1)),
                    ComputationTree.VariableNodeComputationTree("y" + it.drop(1))
                )
            }.associateBy { it.variableName }
        val zComputations = directComputationMap.filter { it.key.startsWith('z') }
            .values
            .sortedBy { it.variableName }
            .map { it.complete(computationTreeLookup) }

        val notXors = zComputations.filter { it !is ComputationTree.XorVariableComputationTree }

        // - 0, 1, 45
        val incorrectZComps = zComputations.filter { it !is ComputationTree.XorVariableComputationTree ||
            !((it.left is ComputationTree.XorVariableComputationTree && it.right is ComputationTree.OrVariableComputationTree) ||
                (it.right is ComputationTree.XorVariableComputationTree && it.left is ComputationTree.OrVariableComputationTree)
                )
        }

        // set a breakpoint here and figure out by hand, which of the zGates do not match the correct spec.
        TODO("Not yet implemented")
    }
}

fun main() = Day24.run()