package day04

import readInput
fun main() {
    fun part1(input: List<String>): Int =
        input.size

    fun part2(input: List<String>): Int =
        input.size

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day04/Day04_test")
    check(part1(testInput) == 1)
    check(part2(testInput) == 1)

    val input = readInput("day04/Day04")
    println(part1(input))
    println(part2(input))
}