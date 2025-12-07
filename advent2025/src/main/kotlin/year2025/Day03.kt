package year2025

import adventday.AdventDay
import adventday.InputRepresentation

private fun List<Long>.turnOn(toTurnOn: Int, numberPrefix: Long = 0): Long = when (toTurnOn) {
    0 -> numberPrefix
    else -> dropLast(toTurnOn - 1).max().let { nextBiggest ->
        drop(indexOf(nextBiggest) + 1).turnOn(toTurnOn - 1, numberPrefix * 10 + nextBiggest)
    }
}

private fun String.asBank() = map { it.digitToInt().toLong() }

object Day03 : AdventDay(2025, 3, "Lobby") {
    override fun part1(input: InputRepresentation): Long = input
        .lines
        .map { it.asBank() }
        .sumOf { bank ->
            bank.turnOn(2)
        }

    override fun part2(input: InputRepresentation): Long = input
        .lines
        .map { it.asBank() }
        .sumOf { bank ->
            bank.turnOn(12)
        }
}

fun main() = Day03.run()
