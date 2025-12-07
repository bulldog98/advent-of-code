package year2023

import adventday.AdventDay
import adventday.InputRepresentation
import helper.numbers.NUMBERS_REGEX
import helper.numbers.toAllLongs

object Day06: AdventDay(2023, 6, "Wait For It") {
    private fun Pair<Long, Long>.numberOfWaysToWin() =
        (1 until first).count { speedPerMilliSecond ->
            val millisecondsRemaining = first - speedPerMilliSecond
            speedPerMilliSecond * millisecondsRemaining > second
        }

    override fun part1(input: InputRepresentation): Int {
        val times = input.lines[0].toAllLongs()
        val distance = input.lines[1].toAllLongs()
        val games = times.zip(distance)
        return games.fold(1) { cur, game ->
            cur * game.numberOfWaysToWin()
        }
    }

    override fun part2(input: InputRepresentation): Int {
        val time = NUMBERS_REGEX.findAll(input.lines[0]).joinToString("") { it.value }.toLong()
        val distance = NUMBERS_REGEX.findAll(input.lines[1]).joinToString("") { it.value }.toLong()
        val game = time to distance
        return game.numberOfWaysToWin()
    }
}

fun main() = Day06.run()
