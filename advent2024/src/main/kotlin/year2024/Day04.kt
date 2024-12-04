package year2024

import AdventDay
import Point2D

val xmasChars = "XMAS".toSet()

operator fun Int.times(other: Point2D) = Point2D(other.x * this, other.y * this)

fun Point2D.moveInDirection(direction: Point2D, times: Int) = (0..<times).map {
    this + (it * direction)
}

fun Point2D.computeWordDirections(): Set<List<Point2D>> = setOf(
    moveInDirection(Point2D.RIGHT, 4),
    moveInDirection(Point2D.LEFT, 4),
    moveInDirection(Point2D.UP, 4),
    moveInDirection(Point2D.DOWN, 4),
    moveInDirection(Point2D.UP + Point2D.LEFT, 4),
    moveInDirection(Point2D.UP + Point2D.RIGHT, 4),
    moveInDirection(Point2D.DOWN + Point2D.LEFT, 4),
    moveInDirection(Point2D.DOWN + Point2D.RIGHT, 4),
)

fun List<String>.buildXMasField() = buildMap {
    forEachIndexed { y, it ->
        it.forEachIndexed { x, c ->
            if (c in xmasChars) {
                put(Point2D(x, y), c)
            }
        }
    }
}

object Day04 : AdventDay(2024, 4) {
    override fun part1(input: List<String>): Int {
        val field = input.buildXMasField()
        return field.entries.filter { (_, c) -> c == 'X' }.sumOf { (p, _) ->
            p.computeWordDirections().count {
                it.map { p -> field[p] } == "XMAS".toList()
            }
        }
    }

    override fun part2(input: List<String>): Int {
        val field = input.buildXMasField()

        val missingLetters = setOf('M', 'S')
        return field.entries.filter { (_, c) -> c == 'A' }.count { (p, _) ->
            val upperLeft = field[p + Point2D.LEFT + Point2D.UP]
            val downLeft = field[p + Point2D.LEFT + Point2D.DOWN]
            val upperRight = field[p + Point2D.RIGHT + Point2D.UP]
            val downRight = field[p + Point2D.RIGHT + Point2D.DOWN]
            setOf(upperRight, downLeft) == missingLetters && setOf(upperLeft, downRight) == missingLetters
        }
    }
}

fun main() = Day04.run()