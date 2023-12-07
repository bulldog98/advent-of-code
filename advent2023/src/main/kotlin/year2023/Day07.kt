package year2023

import AdventDay

fun Char.cardTypeValue() = when (this) {
    '2' -> 'b'
    '3' -> 'c'
    '4' -> 'd'
    '5' -> 'e'
    '6' -> 'f'
    '7' -> 'g'
    '8' -> 'h'
    '9' -> 'i'
    'T' -> 'j'
    'J' -> 'k'
    'Q' -> 'l'
    'K' -> 'm'
    'A' -> 'n'
    else -> error("can not happen")
}

data class Hand(val cards: String, val bid: Int) {
    val type: Int
        get() {
            val counts = cards.groupingBy { it }.eachCount().values
            return when {
                5 in counts -> 6
                4 in counts -> 5
                3 in counts && 2 in counts -> 4
                3 in counts -> 3
                counts.count { it == 2 } == 2 -> 2
                2 in counts -> 1
                else -> 0
            }
        }
    companion object {
        operator fun invoke(input: List<String>) = Hand(input[0], input[1].toInt())
    }
}

object Day07 : AdventDay(2023, 7) {
    override fun part1(input: List<String>): Int =
        input
            .asSequence()
            .map {
                Hand(it.split(" "))
            }
            .sortedWith(compareBy({ it.type }, { it.cards.map { c -> c.cardTypeValue() }.joinToString("") }))
            .mapIndexed { i, it -> it to i + 1 }
            .sumOf { it.first.bid * it.second }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day07.run()
