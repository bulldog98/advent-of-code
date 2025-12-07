package year2015

import adventday.AdventDay
import adventday.InputRepresentation
import year2015.Day05.component1
import year2015.Day05.component2
import year2015.Day05.component3

private val vowels = "aeiou".toSet()
private val badStrings = setOf("ab", "cd", "pq", "xy")

// typealias so we have parameter names
private typealias Rule = (word: String) -> Boolean

private fun String.allRulesApply(vararg rules: Rule): Boolean = rules.all { rule ->
    rule(this)
}

private fun InputRepresentation.countNiceStrings(vararg rules: Rule): Int = lines
    .count {
        it.allRulesApply(*rules)
    }

object Day05 : AdventDay(2015, 5, "") {
    private operator fun String.component1() = this[0]
    private operator fun String.component2() = this[1]
    private operator fun String.component3() = this[2]

    private data object Has3Vowels : Rule {
        override fun invoke(word: String): Boolean = word.count { c -> c in vowels } >= 3
    }

    private data object AnyCharRepeatsDirectly : Rule {
        override fun invoke(word: String): Boolean = word.windowed(2).any { (a, b) -> a == b }
    }

    private data object NoBadStrings : Rule {
        override fun invoke(word: String): Boolean = word.windowed(2).all { it !in badStrings }
    }

    private data object PairOfCharsIsRepeatedWithoutOverlap : Rule {
        override fun invoke(word: String): Boolean = word.indices
            // pair can only start at 4 last positon
            .take(word.length - 4)
            .any {
                // 2 chars, repeat
                word.substring(it, it + 2) in word.substring(it + 2).windowed(2)
            }
    }

    private data object AnyCharRepeatsDirectlyWithCharBetween : Rule {
        override fun invoke(word: String): Boolean = word.windowed(3).any { (a, _, b) -> a == b }
    }

    override fun part1(input: InputRepresentation): Int = input
        .countNiceStrings(
            Has3Vowels,
            AnyCharRepeatsDirectly,
            NoBadStrings
        )

    override fun part2(input: InputRepresentation): Int = input
        .countNiceStrings(
            PairOfCharsIsRepeatedWithoutOverlap,
            AnyCharRepeatsDirectlyWithCharBetween
        )
}

fun main() = Day05.run()
