package year2015

import adventday.AdventDay
import adventday.InputRepresentation

object Day08 : AdventDay(2015, 8, "Matchsticks") {
    private val escapedBackspace = Regex("""\\\\""")
    private val unescapedBackspace = Regex("""\\""")
    private val escapedQuote = Regex("""\\"""")
    private val unescapedQuote = Regex("\"")
    private val escapedAsci = Regex("""\\x[\d,a-f][\d,a-f]""")

    override fun part1(input: InputRepresentation): Long = input
        .lines
        .sumOf {
            val chars = it.length.toLong()
            val content = it.drop(1).dropLast(1)
                .replace(escapedBackspace, "1")
                .replace(escapedQuote, "1")
                .replace(escapedAsci, "1")
            chars - content.length
        }

    override fun part2(input: InputRepresentation): Long = input
        .lines
        .sumOf {
            val chars = it.length.toLong()

            val content = it
                .replace(unescapedBackspace, "11")
                .replace(unescapedQuote, "11")
            // the quotes + actual escaped content - length of input
            2 + content.length - chars
        }
}

fun main() = Day08.run()
