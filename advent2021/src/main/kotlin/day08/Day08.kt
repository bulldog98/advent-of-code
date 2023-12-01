package day08

import readInput

val uniqueCountOfSignals = listOf(2, 3, 4, 7)

private fun String.decode(): Int = Encoder(this).value

fun main() {
    fun part1(input: List<String>): Int =
        input.sumOf {
            it.split("|")[1]
                .split(" ")
                .count { digits ->
                    digits.length in uniqueCountOfSignals
                }
        }

    fun part2(input: List<String>): Int =
        input.sumOf { it.decode() }

    val testInput = readInput("day08/Day08_test")
    val input = readInput("day08/Day08")

    // test if implementation meets criteria from the description:
    check(part1(testInput) == 26)
    println(part1(input))

    // test if implementation meets criteria from the description:
    check(part2(testInput) == 61229)
    println(part2(input))
}
