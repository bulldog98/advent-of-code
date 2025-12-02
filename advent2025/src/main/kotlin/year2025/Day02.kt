package year2025

import adventday.AdventDay
import adventday.InputRepresentation

private fun String.isRepeatPattern(): Boolean {
    var i = 1
    while (i <= length / 2) {
        if (drop(i) in dropLast(length - i)) {
            return true
        }
        i++
    }
    return false
}

private fun String.isOnlyRepeatingSubstring(): Boolean {
    var i = 1
    while (i <= length / 2) {
        if (take(i) in drop(i)) {
            var string = take(i)
            while (string.length < length) {
                string += take(i)
            }
            if (string == this) {
                return true
            }
        }
        i++
    }
    return false
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
