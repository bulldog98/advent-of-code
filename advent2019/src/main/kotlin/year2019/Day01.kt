package year2019

import adventday.AdventDay
import adventday.InputRepresentation

object Day01: AdventDay(2019, 1) {
    override fun part1(input: InputRepresentation): Long = input.sumOf {
        it.toLong() / 3 - 2
    }

    override fun part2(input: InputRepresentation): Long {
        TODO("Not yet implemented")
    }
}

fun main() = Day01.run()