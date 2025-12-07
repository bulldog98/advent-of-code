package year2015

import adventday.AdventDay
import adventday.InputRepresentation

object Day01 : AdventDay(2015, 1, "Not Quite Lisp") {
    override fun part1(input: InputRepresentation): Long =
        input.lines[0].sumOf {
            when (it) {
                '(' -> 1L
                ')' -> -1L
                else -> error("not able to parse")
            }
        }

    override fun part2(input: InputRepresentation): Int =
        input.lines[0].runningFold(0L) { acc, it ->
            acc + when (it) {
                '(' -> 1L
                ')' -> -1L
                else -> error("not able to parse")
            }
        }.indexOfFirst { it == -1L }
}

fun main() = Day01.run()
