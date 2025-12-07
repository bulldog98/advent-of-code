package year2022

import adventday.AdventDay
import adventday.InputRepresentation
import kotlin.math.pow

private fun Char.parseSnafuDigit() = when (this) {
    '2' -> 2L
    '1' -> 1L
    '0' -> 0L
    '-' -> -1L
    '=' -> -2L
    else -> error("unexpected Snafu digit")
}

fun String.parseSnafuNumber(): Long =
    map { it.parseSnafuDigit() }.reversed().reduceIndexed { i, acc, cur ->
        acc + (5.0.pow(i)).toLong() * cur
    }

private fun Long.toSnafuDigit(): Char = when (this) {
    0L -> '0'
    1L -> '1'
    2L -> '2'
    3L -> '=' // -2 == 3 mod 5
    4L -> '-' // -1 == 4 mod 5
    else -> error("only can convert digit")
}

fun Long.toSnafuNumber(): String {
    if (this == 0L) return "0"
    return generateSequence(this to "") { (rest, currentString) ->
        val lowestDigit = rest % 5
        (rest + 2) / 5 to lowestDigit.toSnafuDigit() + currentString
    }.first { it.first <= 0L }.second
}

class Day25 : AdventDay(2022, 25, "Full of Hot Air") {
    override fun part1(input: InputRepresentation): Long =
        input.lines.sumOf { it.parseSnafuNumber() }

    override fun part2(input: InputRepresentation): String =
        part1(input).toSnafuNumber()
}

fun main() = Day25().run()