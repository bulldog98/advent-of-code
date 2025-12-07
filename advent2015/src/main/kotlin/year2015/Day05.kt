package year2015

import NotImplementedYet
import adventday.AdventDay
import adventday.InputRepresentation
import year2015.Day05.component1
import year2015.Day05.component2

private val vowels = "aeiou".toSet()
private val badStrings = setOf("ab", "cd", "pq", "xy")

object Day05 : AdventDay(2015, 5, "") {
    private operator fun String.component1() = this[0]
    private operator fun String.component2() = this[1]

    override fun part1(input: InputRepresentation): Int = input
        .lines
        .count { line ->
            line.count { c ->  c in vowels } >= 3 &&
                line.windowed(2).any { (a, b) -> a == b } &&
                line.windowed(2).all { it !in badStrings }

        }

    override fun part2(input: InputRepresentation): Any =
        NotImplementedYet
}

fun main() = Day05.run()