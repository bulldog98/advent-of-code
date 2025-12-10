package year2015.day07

sealed interface Instruction : (Map<String, UShort>) -> UShort {
    fun Map<String, UShort>.lookup(variableOrNumber: String) =
        variableOrNumber.toUShortOrNull() ?: getValue(variableOrNumber)

    data class FixedWire(val valueOrNumber: String) : Instruction {
        override fun invoke(lookupTable: Map<String, UShort>): UShort = lookupTable
            .lookup(valueOrNumber)
    }

    data class And(val left: String, val right: String) : Instruction {
        override fun invoke(lookupTable: Map<String, UShort>): UShort =
            lookupTable.lookup(left).and(lookupTable.lookup(right))
    }

    data class Or(val left: String, val right: String) : Instruction {
        override fun invoke(lookupTable: Map<String, UShort>): UShort =
            lookupTable.lookup(left)
                .or(lookupTable.lookup(right))
    }

    data class LeftShift(val left: String, val right: Int) : Instruction {
        override fun invoke(lookup: Map<String, UShort>): UShort =
            lookup.lookup(left).rotateLeft(right)
    }

    data class RightShift(val left: String, val amount: Int) : Instruction {
        override fun invoke(lookup: Map<String, UShort>): UShort =
            lookup.lookup(left).rotateRight(amount)
    }

    data class Not(val variable: String) : Instruction {
        override fun invoke(lookup: Map<String, UShort>): UShort =
            lookup.lookup(variable).inv()
    }

    companion object {
        fun parse(line: String): Pair<String, Instruction> = line.split(" -> ")
            .let { (left, right) ->
                right to when {
                    left.all { it.isLetterOrDigit() } -> FixedWire(left)
                    left.contains("AND") -> left.split(" AND ").let { (a, b) ->
                        And(a, b)
                    }
                    left.contains("OR") -> left.split(" OR ").let { (a, b) ->
                        Or(a, b)
                    }
                    left.contains("LSHIFT") -> left.split(" LSHIFT ").let { (a, b) ->
                        LeftShift(a, b.toInt())
                    }
                    left.contains("RSHIFT") -> left.split(" RSHIFT ").let { (a, b) ->
                        RightShift(a, b.toInt())
                    }
                    left.startsWith("NOT ") -> Not(left.drop(4))
                    else -> error("Unrecognized input: $left")
                }
            }
    }
}