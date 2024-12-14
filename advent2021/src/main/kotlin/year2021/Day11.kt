package year2021

import adventday.AdventDay
import adventday.InputRepresentation
import year2021.day11.flashCount
import year2021.day11.parseBoard
import year2021.day11.simulateAllSteps

object Day11 : AdventDay(2021, 11) {
    override fun part1(input: InputRepresentation): Int =
        input.parseBoard()
            .simulateAllSteps()
            .take(100)
            .sumOf { it.flashCount }

    override fun part2(input: InputRepresentation): Int =
        input.parseBoard()
            .simulateAllSteps()
            .indexOfFirst { board ->
                board.entries
                    .all { it.value == 0 }
            } + 1
}


fun main() = Day11.run()
