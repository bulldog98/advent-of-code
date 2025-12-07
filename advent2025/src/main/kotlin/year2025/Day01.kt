package year2025

import adventday.AdventDay
import adventday.InputRepresentation
import year2025.day01.Dial
import year2025.day01.Instruction
import year2025.day01.plus

object Day01 : AdventDay(2025, 1, "Secret Entrance") {
    override fun part1(input: InputRepresentation): Int = input
        .lines
        .map { it: String -> Instruction.parse(it) }
        .runningFold(Dial()) { acc, instruction ->
            acc + instruction
        }
        .count { it.position == 0 }

    override fun part2(input: InputRepresentation): Int = input
        .lines
        .map { it: String -> Instruction.parse(it) }
        .fold(Dial()) { dial, instruction ->
            dial + instruction
        }.clicks
}

fun main() = Day01.run()
