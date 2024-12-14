package year2024

import AdventDay
import helper.numbers.toAllLongs
import kotlin.math.abs

object Day01 : AdventDay(2024, 1) {
    override fun part1(input: List<String>): Long {
        val (leftList, right) = input.map {
            it.toAllLongs().zipWithNext().first()
        }.unzip()

        return leftList.sorted().zip(right.sorted()).sumOf { (left, right) -> abs(left - right) }
    }

    override fun part2(input: List<String>): Long {
        val firstList = input.map { it.substringBefore(" ").toLong() }
        val secondCounts = buildMap<Long, Long> {
            input.forEach { line ->
                val num = line.substringAfterLast(" ").toLong()
                this[num] = this.getOrDefault(num, 0L) + 1L
            }
        }
        return firstList.sumOf { it * secondCounts.getOrDefault(it, 0L) }
    }
}

fun main() = Day01.run()