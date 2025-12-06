package year2025.day06

sealed interface Operator {
    fun compute(numbers: List<Long>): Long

    data object Addition : Operator {
        override fun compute(numbers: List<Long>): Long = numbers.sum()
    }

    data object Multiplication : Operator {
        override fun compute(numbers: List<Long>): Long = numbers.fold(1) { acc, next ->
            acc * next
        }
    }

    companion object {
        fun parse(input: String): Operator = parse(input[0])
        fun parse(input: Char): Operator = when (input) {
            '+' -> Addition
            '*' -> Multiplication
            else -> error("unable to parse $input")
        }
    }
}