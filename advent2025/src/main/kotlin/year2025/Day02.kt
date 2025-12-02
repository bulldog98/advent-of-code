package year2025

import adventday.AdventDay
import adventday.InputRepresentation

private fun String.isRepeatPattern(): Boolean = length / 2 * 2 == length &&
    take(length / 2 ) + take(length / 2) == this

private operator fun String.times(count: Int): String = timesHelper(count)

private tailrec fun String.timesHelper(count: Int, prefix: String = ""): String = when (count) {
    0 -> prefix
    else -> timesHelper(count - 1, prefix + this)
}

private fun String.isOnlyRepeatingSubstring(): Boolean = (1 ..< length).any {
    length / it * it == length && take(it) * (length / it) == this
}

object Day02 : AdventDay(2025, 2) {
    override fun part1(input: InputRepresentation): Long = input[0]
        .split(",")
        .flatMap { it.split("-").let { (a, b) -> (a.toLong()..b.toLong()) } }
        .filter { it.toString().isRepeatPattern() }
        .sumOf { it }

    override fun part2(input: InputRepresentation): Long = input[0]
        .split(",")
        .flatMap { it.split("-").let { (a, b) -> (a.toLong()..b.toLong()) } }
        .filter { it.toString().isOnlyRepeatingSubstring() }
        .sumOf { it }
}

fun main() = Day02.run()
