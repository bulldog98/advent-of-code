package year2022

import AdventDay

class Day01 : AdventDay(2022, 1) {
    private fun List<String>.calculateCalories(): List<Int> =
        map {
            it.toIntOrNull()
        }.
        foldRight(listOf(0)) { num, acc: List<Int> ->
            if (num === null) {
                acc + listOf(0)
            } else {
                val start = acc.dropLast(1)
                start + listOf(acc.last() + num)
            }
        }.toList()

    override fun part1(input: List<String>): Int =
        input.calculateCalories().maxOrNull() ?: 0

    override fun part2(input: List<String>): Int =
        input.calculateCalories().sortedDescending().take(3).sum()
}

fun main() = Day01().run()