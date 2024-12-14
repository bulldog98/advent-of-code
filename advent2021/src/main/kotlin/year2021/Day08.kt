package year2021

import adventday.AdventDay
import adventday.InputRepresentation
import year2021.day08.Encoder

val uniqueCountOfSignals = listOf(2, 3, 4, 7)

private fun String.decode(): Int = Encoder(this).value

object Day08 : AdventDay(2021, 8) {
    override fun part1(input: InputRepresentation): Int =
        input.sumOf {
            it.split("|")[1]
                .split(" ")
                .count { digits ->
                    digits.length in uniqueCountOfSignals
                }
        }

    override fun part2(input: InputRepresentation): Int =
        input.sumOf { it.decode() }
}

fun main() = Day08.run()
