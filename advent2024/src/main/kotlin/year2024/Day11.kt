package year2024

import AdventDay
import helper.numbers.toAllLongs

fun Long.oneStep(): List<Long> = when {
    this == 0L -> listOf(1)
    "$this".length % 2 == 0 -> {
        val stringRepresentation = "$this"
        listOf(
            stringRepresentation.take(stringRepresentation.length / 2).toLong(),
            stringRepresentation.drop(stringRepresentation.length / 2).toLong()
        )
    }
    else -> listOf(this * 2024)
}

object Day11 : AdventDay(2024, 11) {
    override fun part1(input: List<String>): Int {
        val startingStones = input[0].toAllLongs().toList()
        return generateSequence(startingStones) {
            it.flatMap { stone -> stone.oneStep() }
        }
            .drop(25)
            .first()
            .size
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day11.run()