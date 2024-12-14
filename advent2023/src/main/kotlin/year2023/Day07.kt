package year2023

import adventday.AdventDay
import year2023.day07.Hand

fun Char.cardTypeValue(jokerBad: Boolean = false) = when (this) {
    '2' -> 'b'
    '3' -> 'c'
    '4' -> 'd'
    '5' -> 'e'
    '6' -> 'f'
    '7' -> 'g'
    '8' -> 'h'
    '9' -> 'i'
    'T' -> 'j'
    'J' -> if (jokerBad) 'a' else 'k'
    'Q' -> 'l'
    'K' -> 'm'
    'A' -> 'n'
    else -> error("can not happen")
}

fun String.asCardTypeValues(jokerBad: Boolean = false) =
    map { c -> c.cardTypeValue(jokerBad) }.joinToString("")

object Day07 : AdventDay(2023, 7) {
    override fun part1(input: List<String>): Int =
        input
            .asSequence()
            .map {
                Hand(it.split(" "))
            }
            .sortedWith(compareBy({ it.type }, { it.cards.asCardTypeValues() }))
            .mapIndexed { i, it -> it to i + 1 }
            .sumOf { it.first.bid * it.second }

    override fun part2(input: List<String>): Any =
        input
            .asSequence()
            .map {
                Hand(it.split(" "), true)
            }
            .sortedWith(compareBy({ it.type }, { it.cards.asCardTypeValues(true) }))
            .mapIndexed { i, it -> it to i + 1 }
            .also { it.forEach(::println) }
            .sumOf { it.first.bid * it.second }
}

fun main() = Day07.run()
