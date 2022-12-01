package day02

import readInput

fun part1(input: List<String>): Int = input.size
fun part2(input: List<String>): Int = input.size

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day02/Day02_test")
    check(part1(testInput) == 1)
//    check(part2(testInput) == 1)

    val input = readInput("day02/Day02")
    println(part1(input))
//    println(part2(input))
}