package year2023

import AdventDay

object Day09: AdventDay(2023, 9) {
    private fun Sequence<Long>.computeHistory() =
        generateSequence(this.toList()) { lastRow ->
            lastRow.windowed(2).map { (a, b) ->
                b - a
            }
        }.takeWhile { it.any { n -> n != 0L } }

    private fun Sequence<Long>.extrapolateNextValue(): Long =
        computeHistory().sumOf { it.last() }

    override fun part1(input: List<String>): Long =
        input.sumOf { line -> line.split(" ").map { it.toLong() }.asSequence().extrapolateNextValue() }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day09.run()
