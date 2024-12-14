package year2023

import adventday.AdventDay
import year2023.day04.Card

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
