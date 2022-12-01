package day01

import readInput

fun List<Int?>.splitOnNull(): List<List<Int>> =
    this.foldRight(listOf(listOf())) { num, acc: List<List<Int>> ->
        if (num === null) {
            acc + listOf(listOf())
        } else {
            val start = acc.dropLast(1)
            start + listOf(acc.last() + num)
        }
    }.toList()


fun main() {
    fun List<String>.calculateCalories(): List<Int> =
        map {
            it.toIntOrNull()
        }
        .splitOnNull()
        .map {
            it.sum()
        }

    fun part1(input: List<String>): Int =
        input.calculateCalories().maxOrNull() ?: 0

    fun part2(input: List<String>): Int =
        input.calculateCalories().sortedDescending().take(3).sum()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day01/Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("day01/Day01")
    println(part1(input))
    println(part2(input))
}
