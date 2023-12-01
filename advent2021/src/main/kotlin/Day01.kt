object Day01 : AdventDay(2021, 1) {
    override fun part1(input: List<String>): Int {
        return input.map { it.toInt() }.fold(-1 to -1) { (sum, last), curr ->
            val increase = if (last < curr) 1 else 0
            (sum + increase)  to curr
        }.first
    }

    override fun part2(input: List<String>): Int {
        return input.map { it.toInt() }.windowed(3).fold(-1 to -1) { (sum, last), (a, b, c) ->
            val curr = a + b + c
            val increase = if (last < curr) 1 else 0
            val newSum = sum + increase
            newSum to curr
        }.first
    }
}

fun main() = Day01.run()
