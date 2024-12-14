package year2021

import adventday.AdventDay
import adventday.InputRepresentation
import year2021.day05.Board
import year2021.day05.ComplexLine
import year2021.day05.SimpleLine

object Day05 : AdventDay(2021, 5) {
    override fun part1(input: InputRepresentation): Int {
        val board = Board(input.map(::SimpleLine))
        return board.count { (_, i) -> i > 1 }
    }

    override fun part2(input: InputRepresentation): Int {
        val board = Board(input.map(::ComplexLine))
        return board.count { (_, i) -> i > 1 }
    }
}

fun main() = Day05.run()
