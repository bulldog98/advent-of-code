package year2015

import adventday.AdventDay
import adventday.InputRepresentation

object Day01 : AdventDay(2015, 1, "Not Quite Lisp") {
    private fun Char.toFloorDifference() = when (this) {
        '(' -> 1
        ')' -> -1
        else -> error("not able to parse")
    }

    override fun part1(input: InputRepresentation): Int = input
        .lines[0]
        .sumOf {
            it.toFloorDifference()
        }

    override fun part2(input: InputRepresentation): Int = input
        .lines[0]
        .runningFold(0) { acc, it ->
            acc + it.toFloorDifference()
        }.indexOfFirst { it == -1 }
}

fun main() = Day01.run()
