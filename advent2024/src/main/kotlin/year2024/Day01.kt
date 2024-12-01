package year2024

import AdventDay
import kotlin.math.abs

object Day01 : AdventDay(2024, 1) {
    override fun part1(input: List<String>): Long {
        val firstList = input.map { it.takeWhile(Char::isDigit).toLong() }.sorted()
        val secondList = input.map { it.reversed().takeWhile(Char::isDigit).reversed().toLong() }.sorted()

        return firstList.indices.sumOf { abs(secondList[it] - firstList[it]) }
    }

    override fun part2(input: List<String>): Long {
        val firstList = input.map { it.takeWhile(Char::isDigit).toLong() }
        val secondCounts = buildMap<Long, Long> {
            input.forEach { line ->
                val num = line.reversed().takeWhile(Char::isDigit).reversed().toLong()
                this[num] = this.getOrDefault(num, 0L) + 1L
            }
        }
        return firstList.sumOf { it * secondCounts.getOrDefault(it, 0L) }
    }
}

fun main() = Day01.run()