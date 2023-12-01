package day01

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { it.toInt() }.fold(-1 to -1) { (sum, last), curr ->
            val increase = if (last < curr) 1 else 0
            (sum + increase)  to curr
        }.first
    }

    fun part2(input: List<String>): Int {
        return input.map { it.toInt() }.windowed(3).fold(-1 to -1) { (sum, last), (a, b, c) ->
            val curr = a + b + c
            val increase = if (last < curr) 1 else 0
            val newSum = sum + increase
            newSum to curr
        }.first
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day01/Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("day01/Day01")
    println(part1(input))
    println(part2(input))
}
