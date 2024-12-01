package year2024

import AdventDay
import kotlin.math.abs

object Day01 : AdventDay(2024, 1) {
    override fun part1(input: List<String>): Long {

        val firstList = input.map { it.takeWhile(Char::isDigit).toLong() }.sorted()
        val secondList = input.map { it.reversed().takeWhile(Char::isDigit).reversed().toLong() }.sorted()

        var diff = 0L
        for (i in firstList.indices) {
            diff += abs(secondList[i] - firstList[i])
        }
        return diff
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day01.run()