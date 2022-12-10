fun String.findXUniqueChars(x: Int) = windowedSequence(x)
    .indexOfFirst { it.toSet().size == x } + x

class Day06 : AdventDay(2022, 6) {
    override fun part1(input: List<String>): Int = input
        .first()
        .findXUniqueChars(4)
    override fun part2(input: List<String>): Int = input
        .first()
        .findXUniqueChars(14)
}


fun main() = Day06().run()