package year2024

import AdventDay

private const val PARSE_MULTIPLICATIONS = """mul\((\d+),(\d+)\)"""

object Day03 : AdventDay(2024, 3) {
    override fun part1(input: List<String>): Long =
        PARSE_MULTIPLICATIONS.toRegex()
            .findAll(input.joinToString(""))
            .sumOf {
                it.groupValues[1].toLong() * it.groupValues[2].toLong()
            }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day03.run()