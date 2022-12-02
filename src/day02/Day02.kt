package day02

import day02.Shape.*
import day02.Outcome.*
import readInput

private typealias Strategy = (String, String) -> Shape

private enum class Shape(val points: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3)
}

private enum class Outcome(val points: Int) {
    LOSS(0),
    DRAW(3),
    WIN(6)
}

private infix fun Shape.computeOutcome(other: Shape): Outcome = when (other) {
    this -> DRAW
    this.getBeatenBy() -> WIN
    else -> LOSS
}

private fun Shape(value: String): Shape = when (value) {
    "X", "A" -> ROCK
    "Y", "B" -> PAPER
    else -> SCISSORS
}

private fun Shape.getBeatenBy(): Shape = when (this) {
    ROCK -> PAPER
    PAPER -> SCISSORS
    else -> ROCK
}

private fun score(opponent: Shape, you: Shape): Int = you.points + (opponent computeOutcome you).points

private fun strategyPart1(a: String, b: String): Shape = when {
    Shape(a) == Shape(b) -> Shape(a)
    Shape(b) == Shape(a).getBeatenBy() -> Shape(a).getBeatenBy()
    else -> Shape(a).getBeatenBy().getBeatenBy()
}

private fun strategyPart2(opponent: String, goal: String): Shape = when (goal) {
    // Lose
    "X" -> Shape(opponent).getBeatenBy().getBeatenBy()
    // Draw
    "Y" -> Shape(opponent)
    // Win
    else -> Shape(opponent).getBeatenBy()
}

private fun score(row: String, strategy: Strategy): Int {
    val (a, b) = row.split(" ")
    return score(Shape(a), strategy(a, b))
}

private fun part1(input: List<String>): Int = input.sumOf { score(it, ::strategyPart1) }
private fun part2(input: List<String>): Int = input.sumOf { score(it, ::strategyPart2) }

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day02/Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("day02/Day02")
    println(part1(input))
    println(part2(input))
}