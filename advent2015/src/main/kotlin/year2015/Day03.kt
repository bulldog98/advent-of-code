package year2015

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import year2015.Day03.component1
import year2015.Day03.component2

object Day03 : AdventDay(2015, 3, "Perfectly Spherical Houses in a Vacuum") {
    private fun Char.toDirection() = when (this) {
        '>' -> Point2D.RIGHT
        '<' -> Point2D.LEFT
        '^' -> Point2D.UP
        'v' -> Point2D.DOWN
        else -> error("not supported direction")
    }
    private operator fun String.component1() = this[0]
    private operator fun String.component2() = this[1]

    override fun part1(input: InputRepresentation): Int = input
        .lines[0]
        .runningFold(Point2D.ORIGIN) { acc, instruction ->
            acc + instruction.toDirection()
        }
        .toSet()
        .size

    override fun part2(input: InputRepresentation): Int = input
        .lines[0]
        .chunked(2)
        .runningFold(Point2D.ORIGIN to Point2D.ORIGIN) { (santa, roboSanta), (santaInstruction, roboSantaInstruction) ->
            santa + santaInstruction.toDirection() to roboSanta + roboSantaInstruction.toDirection()
        }
        .flatMap { (a, b) -> listOf(a, b) }
        .toSet()
        .size
}

fun main() = Day03.run()