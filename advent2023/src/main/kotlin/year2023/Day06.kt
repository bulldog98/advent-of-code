package year2023

import adventday.AdventDay
import helper.numbers.NUMBERS_REGEX
import helper.numbers.toAllLongs

object Day06: AdventDay(2023, 6) {
    private fun Pair<Long, Long>.numberOfWaysToWin() =
        (1 until first).count {
            val millisecondsRemaining = first - it
            val speedPerMilliSecond = it
            speedPerMilliSecond * millisecondsRemaining > second
        }

    override fun part1(input: List<String>): Int {
        val times = input[0].toAllLongs()
        val distance = input[1].toAllLongs()
        val games = times.zip(distance)
        return games.fold(1) { cur, game ->
            cur * game.numberOfWaysToWin()
        }
    }

    override fun part2(input: List<String>): Int {
        val time = NUMBERS_REGEX.findAll(input[0]).joinToString("") { it.value }.toLong()
        val distance = NUMBERS_REGEX.findAll(input[1]).joinToString("") { it.value }.toLong()
        val game = time to distance
        return game.numberOfWaysToWin()
    }
}

fun main() = Day06.run()
