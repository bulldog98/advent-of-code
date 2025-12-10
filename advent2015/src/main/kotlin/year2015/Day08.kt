package year2015

import NotYetImplemented
import adventday.AdventDay
import adventday.InputRepresentation

object Day08 : AdventDay(2015, 8, "Matchsticks") {
    private val escapedBackspace = Regex("""\\\\""")
    private val escapedQuote = Regex("""\\"""")
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

    override fun part2(input: InputRepresentation): Any =
        NotYetImplemented
}

fun main() = Day08.run()
