package year2025

import adventday.AdventDay
import adventday.InputRepresentation

object Day03 : AdventDay(2025, 3) {
    override fun part1(input: InputRepresentation): Int = input
        .map { bank -> bank.toCharArray().map { "$it".toInt() } }
        .sumOf {
            val largestDigit = it.dropLast(1).max()
            val secondLargestDigit = it.drop(it.indexOf(largestDigit) + 1).max()

            largestDigit * 10 + secondLargestDigit
        }

    override fun part2(input: InputRepresentation): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day03.run()
