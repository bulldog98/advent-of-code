package year2023

import AdventDay
import year2023.day02.Game

object Day02 : AdventDay(2023, 2) {
    private val allowedMax = mapOf(
        "red" to 12,
        "green" to 13,
        "blue" to 14
    )
    override fun part1(input: List<String>): Int =
        input.sumOf {
            Game(it).let { game ->
                if (game.isPossibleWith(allowedMax))
                    game.id
                else
                    0
            }
        }

    override fun part2(input: List<String>): Int =
        input.map { Game(it) }
            .sumOf { it.fewestPossible().power }
}

fun main() = Day02.run()
