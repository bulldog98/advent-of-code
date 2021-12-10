fun main() {
    fun part1(input: List<String>): Int =
        input.size

    fun part2(input: List<String>): Int =
        input.size

    val testInput = readInput("Day09_test")
    val input = readInput("Day09")

    // test if implementation meets criteria from the description:
    check(part1(testInput) == 1)
    println(part1(input))

    // test if implementation meets criteria from the description:
    check(part2(testInput) == 1)
    println(part2(input))
}