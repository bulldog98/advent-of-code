class Day21 : AdventDay(2022, 21) {
    enum class MathOperation(val separator: String, val computation: (Long, Long) -> Long) {
        PLUS(" + ", Long::plus),
        MINUS(" - ", Long::minus),
        MULTIPLICATION(" * ", Long::times),
        DIVISION(" / ", Long::div)
    }
    sealed interface MonkeyEquation {
        val name: String
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
                    else -> Number(name, rest.toLong())
                }
            }
        }
    }
    data class Number(override val name: String, val value: Long) : MonkeyEquation {
        override fun toString(): String = "$name = $value"
    }
    data class Operation(override val name: String, val a: String, val b: String, val op: MathOperation): MonkeyEquation {
        override fun toString(): String = "$name = $a${op.separator}$b"
    }

    private fun computeOperationMap(input: List<String>): Map<String, MonkeyEquation> {
        val ops = input.map { MonkeyEquation.from(it) }
        val map = buildMap {
            ops.forEach {
                this[it.name] = it
            }
            val operationsToSimplify = this.values.filterIsInstance<Operation>().filter {
                isComputable(it.a, this) && isComputable(it.b, this)
            }.toMutableSet()
            while (operationsToSimplify.isNotEmpty()) {
                val toSimplify = operationsToSimplify.first()
                operationsToSimplify.remove(toSimplify)
                this[toSimplify.name] = Number(toSimplify.name, evaluate(toSimplify.name, this::getValue))
                operationsToSimplify.addAll(this.values.filterIsInstance<Operation>().filter {
                    isComputable(it.a, this) && isComputable(it.b, this)
                })
            }
        }
        return map
    }

    private fun evaluate(variable: String, evaluation: (String) -> MonkeyEquation): Long =
        when (val op = evaluation(variable)) {
            is Number -> op.value
            is Operation -> op.op.computation(evaluate(op.a, evaluation), evaluate(op.b, evaluation))
        }

    private fun isComputable(variable: String, lookupMonkeyEquation: Map<String, MonkeyEquation>): Boolean =
        when (val equation = lookupMonkeyEquation[variable]) {
            null -> false
            is Number -> true
            is Operation -> isComputable(equation.a, lookupMonkeyEquation) && isComputable(equation.b, lookupMonkeyEquation)
        }

    private fun input(eq: MonkeyEquation, lookupMonkeyEquation: (String) -> MonkeyEquation): String = when {
        eq is Number -> eq.value.toString()
        eq is Operation -> {
            val aAsString = if (eq.a == "humn") eq.a else input(lookupMonkeyEquation(eq.a), lookupMonkeyEquation)
            val bAsString = if (eq.b == "humn") eq.a else input(lookupMonkeyEquation(eq.b), lookupMonkeyEquation)
            "($aAsString${eq.op.separator}$bAsString)"
        }
        else -> error("no other possibilities")
    }

    override fun part1(input: List<String>): Long {
        val lookupOperation = computeOperationMap(input)
        return evaluate("root", lookupOperation::getValue)
    }
    override fun part2(input: List<String>): Long {
        val (a, b) = input.find { it.startsWith("root: ") }!!.drop(6).split(" + ")
        val lookupEquation = computeOperationMap(input.filter { "root: " !in it && "humn: " !in it })
        val shouldBe = lookupEquation[b] as Number
        println("${input(lookupEquation[a]!!, lookupEquation::getValue)} = ${shouldBe.value}")
        println("input above equation into a solver")
        return -1
    }
}

fun main() = Day21().run()