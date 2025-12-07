package year2015

import adventday.AdventDay
import adventday.InputRepresentation
import year2015.Day05.component1
import year2015.Day05.component2
import year2015.Day05.component3

private val vowels = "aeiou".toSet()
private val badStrings = setOf("ab", "cd", "pq", "xy")

object Day05 : AdventDay(2015, 5, "") {
    private operator fun String.component1() = this[0]
    private operator fun String.component2() = this[1]
    private operator fun String.component3() = this[2]

    private fun String.has2CharsThatAreTwiceInWithoutOverlap(): Boolean =
        indices
            .take(length - 2)
            .any {
                substring(it, it + 2) in substring(it + 2).windowed(2)
            }

    override fun part1(input: InputRepresentation): Int = input
        .lines
        .count { line ->
            line.count { c ->  c in vowels } >= 3 &&
                line.windowed(2).any { (a, b) -> a == b } &&
                line.windowed(2).all { it !in badStrings }
        }

    override fun part2(input: InputRepresentation): Int = input
        .lines
        .count { line ->
            // any 2 chars, that are twice in the input (no overlap)
            line.has2CharsThatAreTwiceInWithoutOverlap() &&
                // any doubling with char in between
                line.windowed(3).any { (a, _, b) -> a == b }
        }
}

fun main() = Day05.run()
