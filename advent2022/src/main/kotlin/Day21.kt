typealias ValueNumber = Long
private fun String.toValueNumber(): ValueNumber = toLong()

class Day21 : AdventDay(2022, 21) {
    enum class HumanPosition {
        NONE,
        LEFT,
        RIGHT
    }
    enum class MathOperation(
        val separator: String,
        val computation: (ValueNumber, ValueNumber) -> ValueNumber,
        // how to compute first for x = left `op` right, x first param, right is second
        val leftReverse: (ValueNumber, ValueNumber) -> ValueNumber,
        // how to compute second for x = left `op` right, x first param, left is second
        val rightReverse: (ValueNumber, ValueNumber) -> ValueNumber = leftReverse
    ) {
        PLUS(" + ", ValueNumber::plus, ValueNumber::minus),
        MINUS(" - ", ValueNumber::minus, ValueNumber::plus, { result, left -> left - result }),
        MULTIPLICATION(" * ", ValueNumber::times, ValueNumber::div),
        DIVISION(" / ", ValueNumber::div, ValueNumber::times, { result, left -> left / result }),
    }
    sealed interface MonkeyEquation {
        val name: String
        fun value(lookup: (String) -> MonkeyEquation): ValueNumber
        fun containsHumanValue(lookup: (String) -> MonkeyEquation): HumanPosition
        companion object {
            private fun fromEquation(name: String, equation: String, op: MathOperation): Operation =
                equation.split(op.separator).let { (a, b) ->
                    Operation(name, a, b, op)
                }
            fun from(input: String): MonkeyEquation {
                val (name, rest) = input.split(": ")
                return when {
                    MathOperation.PLUS.separator in rest -> fromEquation(name, rest, MathOperation.PLUS)
                    MathOperation.MINUS.separator in rest -> fromEquation(name, rest, MathOperation.MINUS)
                    MathOperation.MULTIPLICATION.separator in rest -> fromEquation(name, rest, MathOperation.MULTIPLICATION)
                    MathOperation.DIVISION.separator in rest -> fromEquation(name, rest, MathOperation.DIVISION)
                    else -> Number(name, rest.toValueNumber())
                }
            }
        }
    }
    object Unknown : MonkeyEquation {
        override val name: String = "humn"
        override fun containsHumanValue(lookup: (String) -> MonkeyEquation): HumanPosition = HumanPosition.LEFT
        override fun value(lookup: (String) -> MonkeyEquation): ValueNumber = ValueNumber.MAX_VALUE
    }
    data class Number(override val name: String, val value: ValueNumber) : MonkeyEquation {
        override fun toString(): String = "$name = $value"
        override fun value(lookup: (String) -> MonkeyEquation): ValueNumber = value
        override fun containsHumanValue(lookup: (String) -> MonkeyEquation): HumanPosition = HumanPosition.NONE
    }
    data class Operation(
        override val name: String,
        val a: String,
        val b: String,
        val op: MathOperation
    ): MonkeyEquation {
        override fun toString(): String = "$name = $a${op.separator}$b"
        override fun value(lookup: (String) -> MonkeyEquation): ValueNumber =
            op.computation(lookup(a).value(lookup), lookup(b).value(lookup))
        override fun containsHumanValue(lookup: (String) -> MonkeyEquation): HumanPosition = when {
            a == "humn" -> HumanPosition.LEFT
            b == "humn" -> HumanPosition.RIGHT
            else -> {
                val (left, right) = listOf(lookup(a), lookup(b)).map { it.containsHumanValue(lookup) }
                when {
                    left != HumanPosition.NONE -> HumanPosition.LEFT
                    right != HumanPosition.NONE -> HumanPosition.RIGHT
                    else -> HumanPosition.NONE
                }
            }
        }
    }

    private fun computeOperationMap(input: List<String>): Map<String, MonkeyEquation> {
        val ops = input.map { MonkeyEquation.from(it) }
        return buildMap {
            ops.forEach {
                this[it.name] = it
            }
            val operationsToSimplify = this.values.filterIsInstance<Operation>().filter {
                isComputable(it.a, this) && isComputable(it.b, this)
            }.toMutableSet()
            while (operationsToSimplify.isNotEmpty()) {
                val toSimplify = operationsToSimplify.first()
                operationsToSimplify.remove(toSimplify)
                this[toSimplify.name] = Number(toSimplify.name, this[toSimplify.name]!!.value(this::getValue))
                operationsToSimplify.addAll(this.values.filterIsInstance<Operation>().filter {
                    isComputable(it.a, this) && isComputable(it.b, this)
                })
            }
        }
    }

    private fun isComputable(variable: String, lookupMonkeyEquation: Map<String, MonkeyEquation>): Boolean =
        when (val equation = lookupMonkeyEquation[variable]) {
            null -> false
            is Unknown -> false
            is Number -> true
            is Operation -> isComputable(equation.a, lookupMonkeyEquation) && isComputable(equation.b, lookupMonkeyEquation)
        }

    private tailrec fun MonkeyEquation.solve(
        expectedValue: ValueNumber,
        lookup: (String) -> MonkeyEquation
    ): ValueNumber = when {
        this is Unknown -> expectedValue
        this is Number -> if (value == expectedValue) value else error("did not match")
        this !is Operation -> error("unable to solve $this")
        lookup(a) is Number -> {
            val left = lookup(a)
            val right = lookup(b)
            val computed = op.rightReverse(expectedValue, left.value(lookup))
            right.solve(computed, lookup)
        }
        lookup(b) is Number -> {
            val left = lookup(a)
            val right = lookup(b)
            val computed = op.leftReverse(expectedValue, right.value(lookup))
            left.solve(computed, lookup)
        }
        else -> error("unable to evaluate")
    }

    override fun part1(input: List<String>): ValueNumber {
        val lookupOperation = computeOperationMap(input)
        return lookupOperation["root"]!!.value(lookupOperation::getValue)
    }
    override fun part2(input: List<String>): ValueNumber {
        val (a, b) = input.find { it.startsWith("root: ") }!!.drop(6).split(" + ")
        val lookupEquation = computeOperationMap(input.filter { "root: " !in it && "humn: " !in it }) +
                mapOf("humn" to Unknown)
        val rootEquation = Operation("root", a, b, MathOperation.MINUS)
        return rootEquation.solve(0L, lookupEquation::getValue)
    }
}

fun main() = Day21().run()