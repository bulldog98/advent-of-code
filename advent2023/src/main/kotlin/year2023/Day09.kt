package year2023

import AdventDay
import helper.numbers.toAllLongs

object Day09: AdventDay(2023, 9) {
    private fun Sequence<Long>.computeHistory() =
        generateSequence(this.toList()) { lastRow ->
            lastRow.windowed(2).map { (a, b) ->
                b - a
            }
        }.takeWhile { it.any { n -> n != 0L } }

    private fun Sequence<Long>.extrapolateNextValue(): Long =
        computeHistory()
            .sumOf { it.last() }

    private fun Sequence<Long>.extrapolatePrevValue(): Long =
        computeHistory()
            .map { it.first() }
            .toList()
            .reversed()
            .fold(0L) { cur, next ->
                next - cur
            }

    override fun part1(input: List<String>): Long =
        input.sumOf { it.toAllLongs().extrapolateNextValue() }

    override fun part2(input: List<String>): Long =
        input.sumOf { it.toAllLongs().extrapolatePrevValue() }
}

fun main() = Day09.run()
