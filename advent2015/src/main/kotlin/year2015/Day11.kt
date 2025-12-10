package year2015

import NotYetImplemented
import adventday.AdventDay
import adventday.InputRepresentation
import year2015.Day11.component1
import year2015.Day11.component2
import year2015.Day11.component3

object Day11 : AdventDay(2015, 11, "Corporate Policy") {
    operator fun String.inc(): String = when {
        last() != 'z' -> dropLast(1) + last().inc()
        else -> dropLast(1).inc() + 'a'
    }

    operator fun String.component1() = this[0]
    operator fun String.component2() = this[1]
    operator fun String.component3() = this[2]

    override fun part1(input: InputRepresentation): String = generateSequence(input.lines[0]) {
        it.inc()
    }
        .drop(1)
        .first {
            // three increasing chars in a row (no gaps)
            it.windowed(3).any { (a, b, c) -> a.inc() == b && b.inc() == c } &&
                // no forbidden letters
                it.none { it in setOf('i', 'o', 'l') } &&
                // 2 double letters non overlapping
                it.take(3).let { (a, b, c) ->
                    // take first 2 into account
                    if (a == b && b != c)
                        1
                    else
                        0
                } + it.windowed(3).count { (a, b, c) ->
                    a != b && b == c
                } >= 2
        }

    override fun part2(input: InputRepresentation): Any =
        NotYetImplemented
}

fun main() = Day11.run()
