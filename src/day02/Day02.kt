package day02

import readInput

typealias Strategy = (String, String) -> String

val String.value
    get() = when (this) {
        "X", "A" -> 1
        "Y", "B" -> 2
        else -> 3
    }

val String.winningAgainst: String
    get() = when (this) {
        "A" -> "B"
        "B" -> "C"
        else -> "A"
    }

fun strategyPart1(a: String, b: String): String = when {
    a.value == b.value -> a
    b.value == a.winningAgainst.value -> a.winningAgainst
    else -> a.winningAgainst.winningAgainst
}

fun strategyPart2(opponent: String, goal: String): String = when (goal) {
    // Lose
    "X" -> opponent.winningAgainst.winningAgainst
    // Draw
    "Y" -> opponent
    // Win
    else -> opponent.winningAgainst
}

fun score(opponent: String, you: String): Int = you.value + when {
    opponent.winningAgainst == you -> 6
    you.winningAgainst == opponent -> 0
    else -> 3
}

fun score(row: String, strategy: Strategy): Int {
    val (a, b) = row.split(" ")
    return score(a, strategy(a, b))
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