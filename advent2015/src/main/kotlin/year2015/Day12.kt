package year2015

import NotYetImplemented
import adventday.AdventDay
import adventday.InputRepresentation
import helper.numbers.toAllLongs

object Day12 : AdventDay(2015, 12, "JSAbacusFramework.io") {
    override fun part1(input: InputRepresentation): Long =
        input.text
            .toAllLongs()
            .sum()

    override fun part2(input: InputRepresentation): Any =
        NotYetImplemented
}

fun main() = Day12.run()
