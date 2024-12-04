package year2024

import AdventDay
import Point2D

val xmasChars = "XMAS".toSet()

operator fun Int.times(other: Point2D) = Point2D(other.x * this, other.y * this)

fun Point2D.moveInDirection(direction: Point2D, times: Int) = (0..<times).map {
    this + (it * direction)
}

fun Point2D.computeWordDirections(): Set<List<Point2D>> = buildSet {
    add(moveInDirection(Point2D.RIGHT, 4))
    add(moveInDirection(Point2D.LEFT, 4))
    add(moveInDirection(Point2D.UP, 4))
    add(moveInDirection(Point2D.DOWN, 4))
    add(moveInDirection(Point2D.UP + Point2D.LEFT, 4))
    add(moveInDirection(Point2D.UP + Point2D.RIGHT, 4))
    add(moveInDirection(Point2D.DOWN + Point2D.LEFT, 4))
    add(moveInDirection(Point2D.DOWN + Point2D.RIGHT, 4))
}

object Day04 : AdventDay(2024, 4) {
    override fun part1(input: List<String>): Long {
        val field = buildMap {
            input.forEachIndexed { y, it ->
                it.forEachIndexed { x, c ->
                    if (c in xmasChars) {
                        put(Point2D(x, y), c)
                    }
                }
            }
        }
        return field.entries.sumOf { (p, c) ->
            if (c == 'X') {
                return@sumOf p.computeWordDirections().count {
                    it.map { p -> field[p] } == "XMAS".toList()
                }.toLong()
            }
            return@sumOf 0L
        }
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day04.run()