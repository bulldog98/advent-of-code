package year2023

import AdventDay
import helper.numbers.NUMBERS_REGEX

object Day06: AdventDay(2023, 6) {
    override fun part1(input: List<String>): Any {
        val times = NUMBERS_REGEX.findAll(input[0]).map { it.value.toInt() }
        val distance = NUMBERS_REGEX.findAll(input[1]).map { it.value.toInt() }
        val games = times.zip(distance)
        return games.fold(1) { cur, game ->
            val number = (1 until game.first).count {
                val millisecondsRemaining = game.first - it
                val speedPerMilliSecond = it
                speedPerMilliSecond * millisecondsRemaining > game.second
            }
            cur * number
        }
    }

    override fun part2(input: List<String>): Any {
        val time = NUMBERS_REGEX.findAll(input[0]).joinToString("") { it.value }.toLong()
        val distance = NUMBERS_REGEX.findAll(input[1]).joinToString("") { it.value }.toLong()
        val game = time to distance
        return (1 until game.first).count {
            val millisecondsRemaining = game.first - it
            val speedPerMilliSecond = it
            speedPerMilliSecond * millisecondsRemaining > game.second
        }
    }
}

fun main() = Day06.run()
