package year2021

import AdventDay
import year2021.day05.Board
import year2021.day05.ComplexLine
import year2021.day05.SimpleLine

object Day05 : AdventDay(2021, 5) {
    override fun part1(input: List<String>): Int {
        val board = Board(input.map(::SimpleLine))
        return board.count { (_, i) -> i > 1 }
    }

    override fun part2(input: List<String>): Int {
        val board = Board(input.map(::ComplexLine))
        return board.count { (_, i) -> i > 1 }
    }
}

fun main() = Day05.run()
