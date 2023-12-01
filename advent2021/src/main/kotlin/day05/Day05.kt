package day05

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val board = Board(input.map(::ComplexLine))
        return board.count { (_, i) -> i > 1 }
    }

    fun part2(input: List<String>): Int {
        val board = Board(input.map(::ComplexLine))
        return board.count { (_, i) -> i > 1 }
    }

    val testInput = readInput("day05/Day05_test")
    val input = readInput("day05/Day05")
    // test if implementation meets criteria from the description:
    check(part1(testInput) == 5)
    println(part1(input))

    // test if implementation meets criteria from the description:
    check(part2(testInput) == 12)
    println(part2(input))
}