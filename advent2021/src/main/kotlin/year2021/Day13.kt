package year2021

import AdventDay
import year2021.day13.Instructions

object Day13 : AdventDay(2021, 13) {
    override fun part1(input: List<String>) =
        Instructions(input)
            .folded()
            .drop(1)
            .first()
            .points.size

    // only prints out the board, you have to read the letters yourself
    override fun part2(input: List<String>) =
        Instructions(input)
            .folded()
            .last()
            .fancyPrint()
}

fun main() = Day13.run()
