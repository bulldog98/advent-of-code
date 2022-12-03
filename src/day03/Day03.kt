package day03

import readInput

typealias Rucksack = Pair<Set<Char>, Set<Char>>

val Char.priority
    get() = when {
        this.isLowerCase() -> this - 'a' + 1
        this.isUpperCase() -> this - 'A' + 1 + 26
        else -> error("check input")
    }

fun splitToCompartments(input: String): Pair<Set<Char>, Set<Char>> =
    input.take(input.length / 2).toSet() to input.drop(input.length / 2).toSet()

fun computeInBoth(pack: Rucksack): Char {
    return pack.first.intersect(pack.second).first()
}

fun part1(input: List<String>): Int = input.sumOf { computeInBoth(splitToCompartments(it)).priority }
fun part2(input: List<String>): Int = input.chunked(3).map { it.map { i -> i.toSet()} }.sumOf {
    it[0].intersect(it[1]).intersect(it[2]).first().priority
}

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day03/Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("day03/Day03")
    println(part1(input))
    println(part2(input))
}