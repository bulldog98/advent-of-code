fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day07_test")
    val input = readInput("Day07")
    // test if implementation meets criteria from the description:
    check(part1(testInput) == 1)
    println(part1(input))

    // test if implementation meets criteria from the description:
    check(part2(testInput) == 1)
    println(part2(input))
}