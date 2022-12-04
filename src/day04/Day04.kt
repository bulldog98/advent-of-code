package day04

import readInput

fun parseAssignment(input: String): Pair<IntRange, IntRange> {
    val (a, b) = input.split(",")
    return a.parsePair() to b.parsePair()
}

private fun String.parsePair(): IntRange {
    val (a, b) = split("-")
    return a.toInt() .. b.toInt()
}

infix fun IntRange.contains(other: IntRange): Boolean {
    return first <= other.first && other.last <= last
}

/*
    5-7,7-9 overlaps in a single section, 7.
    2-8,3-7 overlaps all of the sections 3 through 7.
    6-6,4-6 overlaps in a single section, 6.
    2-6,4-8
 */
infix fun IntRange.overlap(other: IntRange): Boolean {
    return other.first in this || last in other || other.last in this || first in other
}

fun main() {
    fun part1(input: List<String>): Int =
        input.filter {
            val (a, b) = parseAssignment(it)
            a contains b || b contains a
        }
            .count()

    fun part2(input: List<String>): Int =
        input.filter {
            val (a, b) = parseAssignment(it)
            a overlap b
        }
            .count()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day04/Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("day04/Day04")
    println(part1(input))
    println(part2(input))
}