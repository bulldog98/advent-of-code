package year2021

import adventday.AdventDay
import adventday.InputRepresentation
import year2021.day10.computeEndState
import year2021.day10.scoreCompletion

object Day10 : AdventDay(2021, 10) {
    override fun part1(input: InputRepresentation): Int = input
        .lines
        .sumOf { line ->
            line.computeEndState().first?.errorScore ?: 0
        }

    override fun part2(input: InputRepresentation): Long = input
        .lines
        .map { it: String -> it.computeEndState() }
        .filter { it.first == null }
        .map { it.second.reversed().scoreCompletion() }
        .sorted()
        .let {
            it[it.size / 2]
        }
}

fun main() = Day10.run()
