package year2025

import adventday.AdventDay
import adventday.InputRepresentation

object Day05 : AdventDay(2025, 5) {
    override fun part1(input: InputRepresentation): Int {
        val (ranges, rest) = input.asSplitByEmptyLine().let { (a, b) ->
            a.lines().map {
                val (i, j) = it.split("-")
                i.toLong()..j.toLong()
            } to b.lines().map { it.toLong() }
        }
        return rest.count { ranges.any { range -> it in range }}
    }

    override fun part2(input: InputRepresentation): Long {
        TODO("Not yet implemented")
    }
}

fun main() = Day05.run()
