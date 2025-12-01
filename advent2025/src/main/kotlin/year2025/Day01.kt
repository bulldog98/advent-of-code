package year2025

import adventday.AdventDay
import adventday.InputRepresentation

object Day01: AdventDay(2025, 1) {
    override fun part1(input: InputRepresentation): Long =
        input.runningFold(50L) { acc, instruction ->
            (acc + when {
                instruction.startsWith("L") -> 100L - instruction.drop(1).toLong()
                instruction.startsWith("R") -> instruction.drop(1).toLong()
                else -> error("invalid")
            }) % 100L
        }.count { it == 0L }.toLong()

    override fun part2(input: InputRepresentation): Long {
        TODO("Not yet implemented")
    }
}

fun main() = Day01.run()