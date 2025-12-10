package year2015

import adventday.AdventDay
import adventday.InputRepresentation
import utils.string.component1
import utils.string.component2
import utils.string.component3

object Day11 : AdventDay(2015, 11, "Corporate Policy") {
    operator fun String.inc(): String = when {
        last() != 'z' -> dropLast(1) + last().inc()
        else -> dropLast(1).inc() + 'a'
    }

    fun String.passwordIsValid(): Boolean =
        // three increasing chars in a row (no gaps)
        this.windowed(3).any { (a, b, c) -> a.inc() == b && b.inc() == c } &&
            // no forbidden letters
            this.none { it in setOf('i', 'o', 'l') } &&
            // 2 double letters non overlapping
            this.take(3).let { (a, b, c) ->
                // take first 2 into account
                if (a == b && b != c)
                    1
                else
                    0
            } + this.windowed(3).count { (a, b, c) ->
            a != b && b == c
        } >= 2

    override fun part1(input: InputRepresentation): String = generateSequence(input.lines[0]) {
        it.inc()
    }
        .drop(1)
        .first { it.passwordIsValid() }

    override fun part2(input: InputRepresentation): String = generateSequence(input.lines[0]) {
        it.inc()
    }
        .drop(1)
        .filter { it.passwordIsValid() }
        // expired again
        .drop(1)
        .first()
}

fun main() = Day11.run()
