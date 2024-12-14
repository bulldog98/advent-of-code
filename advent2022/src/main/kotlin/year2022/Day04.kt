package year2022

import adventday.AdventDay

fun parseAssignment(input: String): Pair<IntRange, IntRange> {
    val (a, b) = input.split(",")
    return a.parsePair() to b.parsePair()
}

private fun String.parsePair(): IntRange {
    val (a, b) = split("-")
    return a.toInt() .. b.toInt()
}

infix fun IntRange.contains(other: IntRange): Boolean =
    first <= other.first && other.last <= last

/*
    5-7,7-9 overlaps in a single section, 7.
    2-8,3-7 overlaps all the sections 3 through 7.
    6-6,4-6 overlaps in a single section, 6.
    2-6,4-8
 */
infix fun IntRange.overlap(other: IntRange): Boolean =
    other.first in this || other.last in this
            || first in other || last in other

class Day04 : AdventDay(2022, 4) {
    override fun part1(input: List<String>) = input.count {
        val (a, b) = parseAssignment(it)
        a contains b || b contains a
    }

    override fun part2(input: List<String>) =
        input.count {
            val (a, b) = parseAssignment(it)
            a overlap b
        }
}

fun main() = Day04().run()
