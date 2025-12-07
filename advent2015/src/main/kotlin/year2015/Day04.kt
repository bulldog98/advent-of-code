package year2015

import NotImplementedYet
import adventday.AdventDay
import adventday.InputRepresentation
import md5

object Day04 : AdventDay(2015, 4, "The Ideal Stocking Stuffer") {
    override fun part1(input: InputRepresentation): Int =
        (0..Int.MAX_VALUE).first {
            (input.lines[0] + "$it").md5().take(5).all { it == '0' }
        }

    override fun part2(input: InputRepresentation): Any =
        NotImplementedYet
}

fun main() = Day04.run()
