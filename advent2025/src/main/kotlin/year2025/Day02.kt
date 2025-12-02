package year2025

import adventday.AdventDay
import adventday.InputRepresentation

private fun String.hasRepeatPattern(): Boolean {
    var i = 1
    while (i <= length / 2) {
        if (drop(i) in dropLast(length - i)) {
            return true
        }
        i++
    }
    return false
}

object Day02 : AdventDay(2025, 2) {
    override fun part1(input: InputRepresentation): Long = input[0]
        .split(",")
        .flatMap { it.split("-").let { (a, b) -> (a.toLong()..b.toLong()) } }
        .filter { it.toString().hasRepeatPattern() }
        .sumOf { it }

    override fun part2(input: InputRepresentation): Int =
        TODO("Not yet implemented")
}

fun main() = Day02.run()
