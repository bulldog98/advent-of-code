package day03

import readInput

fun part1(input: List<String>): Int = input.size
fun part2(input: List<String>): Int = input.size

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day03/Day03_test")
    check(part1(testInput) == 1)
//    check(part2(testInput) == 1)

    val input = readInput("day03/Day03")
    println(part1(input))
    println(part2(input))
}