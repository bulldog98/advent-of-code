package year2023

import AdventDay
import kotlin.math.pow

data class Card(val id: Int, val left: List<Int>, val right: List<Int>) {
    constructor(input: String): this(
        input.substringAfter("Game ").split(":")[0].drop(4).trim().toInt(),
        input.substringAfter(":").split("|")
    )
    constructor(id: Int, restInput: List<String>): this(
        id,
        restInput[0].chunked(3).filter { it.isNotBlank() }.map {
            it.trim().toInt()
        }.toList(),
        restInput[1].chunked(3).filter { it.isNotBlank() }.map {
            it.trim().toInt()
        }.toList(),
    )

    val winningNumbersCount by lazy {
        right.count { c -> c in left }
    }

    val score by lazy {
        if (winningNumbersCount == 0)
            0
        else
            2.toDouble().pow(winningNumbersCount - 1).toInt()
    }
}

object Day04 : AdventDay(2023, 4) {
    override fun part1(input: List<String>) =
        input.map(::Card)
            .sumOf {
                it.score
            }

    override fun part2(input: List<String>): Int {
        val cards = input.map(::Card).map { it to 1 }.toMutableList()
        for (i in cards.indices) {
            val numOfMore = cards[i].first.winningNumbersCount
            val numberOfCard = cards[i].second
            (i + 1 until i + 1 + numOfMore).forEach { j ->
                val pair = cards.getOrNull(j)
                if (pair != null) {
                    cards[j] = pair.first to pair.second + numberOfCard
                }
            }
        }
        return cards.sumOf { it.second }
    }
}

fun main() = Day04.run()
