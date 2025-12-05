package year2021

import adventday.AdventDay
import adventday.InputRepresentation
import collections.pairings
import year2021.day18.SnailFishNumber

object Day18 : AdventDay(2021, 18) {
    fun List<SnailFishNumber>.sumOf() = reduce(SnailFishNumber::plus)

    override fun part1(input: InputRepresentation): Int =
        input.lines.map { it: String -> SnailFishNumber.parse(it) }.reduce(SnailFishNumber::plus)
            .computeMagnitude()

    override fun part2(input: InputRepresentation): Int =
        input.lines.map { it: String -> SnailFishNumber.parse(it) }.pairings()
            .maxOf { (a, b) -> listOf(a + b, b + a).maxOf { it.computeMagnitude() } }
}

fun main() = Day18.run()
