package year2023

import adventday.AdventDay
import adventday.InputRepresentation
import helper.numbers.toAllLongs

object Day09 : AdventDay(2023, 9, "Mirage Maintenance") {
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

    override fun part1(input: InputRepresentation): Long = input
        .lines
        .sumOf { it.toAllLongs().extrapolateNextValue() }

    override fun part2(input: InputRepresentation): Long = input
        .lines
        .sumOf { it.toAllLongs().extrapolatePrevValue() }
}

fun main() = Day09.run()
