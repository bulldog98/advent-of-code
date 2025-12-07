package year2015

import NotImplementedYet
import Point2D
import adventday.AdventDay
import adventday.InputRepresentation

object Day03 : AdventDay(2015, 3, "Perfectly Spherical Houses in a Vacuum") {
    override fun part1(input: InputRepresentation): Int = input
        .lines[0]
        .runningFold(Point2D.ORIGIN) { acc, dir ->
            acc + when (dir) {
                '>' -> Point2D.RIGHT
                '<' -> Point2D.LEFT
                '^' -> Point2D.UP
                'v' -> Point2D.DOWN
                else -> error("not supported direction")
            }
        }
        .toSet()
        .size

    override fun part2(input: InputRepresentation): Any =
        NotImplementedYet
}

fun main() = Day03.run()