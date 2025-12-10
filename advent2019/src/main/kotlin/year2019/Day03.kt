package year2019

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import helper.numbers.toAllLongs
import helper.pair.mapSecond

object Day03 : AdventDay(2019, 3, "Crossed Wires") {
    enum class Direction(val asPoint: Point2D) {
        LEFT(Point2D.LEFT),
        RIGHT(Point2D.RIGHT),
        UP(Point2D.UP),
        DOWN(Point2D.DOWN)
    }

    data class Wire(val directions: List<Pair<Direction, Int>>) {
        private val allPoints by lazy {
            buildSet {
                var lastPoint = Point2D.ORIGIN
                directions.forEach { (direction, length) ->
                    repeat(length) {
                        lastPoint += direction.asPoint
                        add(lastPoint)
                    }
                }
            }
        }

        infix fun crosses(other: Wire): List<Point2D> =
            allPoints.filter { it in other.allPoints }

        fun distanceOfPoint(crossPoint: Point2D) = directions.runningFold(Point2D.ORIGIN) { cur, (dir, length) ->
            cur + (dir.asPoint * length.toLong())
        }.zipWithNext().let {
            val indexToCrossPoint = it.indexOfFirst { (a, b) -> crossPoint in a..b || crossPoint in b..a }
            it.take(indexToCrossPoint + 1).fold(0L) { acc, (a, b) ->
                acc + a.manhattanDistance(if (crossPoint in a..b || crossPoint in b..a) crossPoint else b)
            }
        }

        companion object {
            fun parse(input: String) = Wire(
                input.split(",").map {
                    when (it[0]) {
                        'U' -> Direction.UP to it.toAllLongs()
                        'D' -> Direction.DOWN to it.toAllLongs()
                        'L' -> Direction.LEFT to it.toAllLongs()
                        'R' -> Direction.RIGHT to it.toAllLongs()
                        else -> error("fun")
                    }.mapSecond { longs -> longs.first().toInt() }
                }
            )
        }
    }

    override fun part1(input: InputRepresentation): Long {
        val (wire1, wire2) = input.lines.map { it: String -> Wire.parse(it) }

        return (wire1 crosses wire2).minOf { Point2D.ORIGIN.manhattanDistance(it) }
    }

    override fun part2(input: InputRepresentation): Long {

        val (wire1, wire2) = input.lines.map { it: String -> Wire.parse(it) }

        return (wire1 crosses wire2).minOf { wire1.distanceOfPoint(it) + wire2.distanceOfPoint(it) }
    }
}

fun main() = Day03.run()