package day02

import readInput

typealias Strategy = (String, String) -> Int

fun value(b: String) = when (b) {
    "X", "A" -> 1
    "Y", "B" -> 2
    else -> 3
}

fun winningAgainst(opponent: String): String = when (opponent) {
    "A" -> "B"
    "B" -> "C"
    else -> "A"
}

fun strategyPart1(a: String, b: String): Int = when {
    value(a) == value(b) -> 3 + value(b)
    value(b) == value(winningAgainst(a)) -> 6 + value(b)
    else -> 0 + value(b)
}

fun strategyPart2(opponent: String, goal: String): Int = when (goal) {
    // Lose
    "X" -> value(winningAgainst(winningAgainst(opponent)))
    // Draw
    "Y" -> value(opponent) + 3
    // Win
    else -> 6 + value(winningAgainst(opponent))
}

fun score(a: String, b: String, strategy: Strategy) = when (a) {
    "A" -> strategy(a, b)
    "B" -> strategy(a, b)
    "C" -> strategy(a, b)
    else -> error("")
}

fun score(row: String, strategy: Strategy): Int {
    val (a, b) = row.split(" ")
    return score(a, b, strategy)
}

fun part1(input: List<String>): Int = input.sumOf { score(it, ::strategyPart1) }
fun part2(input: List<String>): Int = input.sumOf { score(it, ::strategyPart2) }

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day02/Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("day02/Day02")
    println(part1(input))
    println(part2(input))
}