package year2023

import adventday.AdventDay

private val additionalDigitMapping = mapOf(
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9"
)
private val additionalDigits = additionalDigitMapping.keys

object Day01 : AdventDay(2023, 1) {
    override fun part1(input: List<String>): Int =
        input.sumOf {
            val onlyDigits = it.filter { c -> c.isDigit() }
            val string = onlyDigits.first().toString() + onlyDigits.last()
            string.toInt()
        }

    override fun part2(input: List<String>): Int = input.sumOf {
        val onlyDigits = it.windowed(5, partialWindows = true).filter { window ->
            window.first().isDigit() || additionalDigits.any { digit -> window.startsWith(digit) }
        }.map {  window ->
            if (window.first().isDigit())
                window.first().toString()
            else
                additionalDigitMapping[additionalDigits.first { d -> window.startsWith(d) }]
        }.joinToString(separator = "")
        val string = onlyDigits.first().toString() + onlyDigits.last()
        string.toInt()
    }
}

fun main() = Day01.run()