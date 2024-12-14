package year2021

import adventday.AdventDay
import year2021.day25.Board

object Day25 : AdventDay(2021, 25) {
    override fun part1(input: List<String>): Int =
        Board(input)
            .simulate()
            .windowed(2)
            .indexOfFirst { (last, now) ->
                last == now
            } + 1

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day25.run()
