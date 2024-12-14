package year2024

import adventday.AdventDay
import adventday.InputRepresentation
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

data class CacheEntry(val stone: Long, val step: Int, val limit: Int)

object Day11 : AdventDay(2024, 11) {
    private val cache = mutableMapOf<CacheEntry, Long>()
    private fun countStones(stone: Long, round: Int, limit: Int): Long = cache.getOrPut(CacheEntry(stone, round, limit)) {
        if (round == limit)
            1
        else
            stone.oneStep().sumOf { countStones(it, round + 1, limit) }
    }

    override fun part1(input: InputRepresentation) = input[0].toAllLongs().sumOf {
        countStones(it, 0, 25)
    }

    override fun part2(input: InputRepresentation) = input[0].toAllLongs().sumOf {
        countStones(it, 0, 75)
    }
}

fun main() = Day11.run()