package year2021

import AdventDay
import year2021.day10.computeEndState
import year2021.day10.scoreCompletion

object Day10 : AdventDay(2021, 10) {
    override fun part1(input: List<String>): Int =
        input.sumOf { line ->
            line.computeEndState().first?.errorScore ?: 0
        }

    override fun part2(input: List<String>): Long =
        input
            .map { it.computeEndState() }
            .filter { it.first == null }
            .map { it.second.reversed().scoreCompletion() }
            .sorted()
            .let {
                it[it.size / 2]
            }
}

fun main() = Day10.run()
