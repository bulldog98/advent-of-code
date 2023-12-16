package year2023

import AdventDay
import Point2D

object Day16 : AdventDay(2023, 16) {
    data class Beam(val point: Point2D, val direction: Point2D)

    fun Beam.next(char: Char) = when (char) {
        '.' -> listOf(copy(point = point + direction))
        '-' -> when (direction) {
            Point2D.RIGHT, Point2D.LEFT -> listOf(copy(point = point + direction))
            Point2D.UP, Point2D.DOWN -> listOf(
                copy(point = point + Point2D.RIGHT, direction = Point2D.RIGHT),
                copy(point = point + Point2D.LEFT, direction = Point2D.LEFT),
            )

            else -> error("invalid direction $direction")
        }

        '|' -> when (direction) {
            Point2D.RIGHT, Point2D.LEFT -> listOf(
                copy(point = point + Point2D.UP, direction = Point2D.UP),
                copy(point = point + Point2D.DOWN, direction = Point2D.DOWN),
            )

            Point2D.UP, Point2D.DOWN -> listOf(copy(point = point + direction))
            else -> error("invalid direction $direction")
        }

        '/' -> when (direction) {
            Point2D.RIGHT -> listOf(copy(point = point + Point2D.UP, direction = Point2D.UP))
            Point2D.UP -> listOf(copy(point = point + Point2D.RIGHT, direction = Point2D.RIGHT))
            Point2D.LEFT -> listOf(copy(point = point + Point2D.DOWN, direction = Point2D.DOWN))
            Point2D.DOWN -> listOf(copy(point = point + Point2D.LEFT, direction = Point2D.LEFT))
            else -> error("invalid direction $direction")
        }

        '\\' -> when (direction) {
            Point2D.RIGHT -> listOf(copy(point = point + Point2D.DOWN, direction = Point2D.DOWN))
            Point2D.UP -> listOf(copy(point = point + Point2D.LEFT, direction = Point2D.LEFT))
            Point2D.LEFT -> listOf(copy(point = point + Point2D.UP, direction = Point2D.UP))
            Point2D.DOWN -> listOf(copy(point = point + Point2D.RIGHT, direction = Point2D.RIGHT))
            else -> error("invalid direction $direction")
        }

        else -> error("invalid char")
    }

    data class FloorMap(val floor: Map<Point2D, Char>): Map<Point2D, Char> by floor {
        val mapXBounds by lazy {
            0..floor.maxOf { it.key.x }
        }
        val mapYBounds by lazy {
            0..floor.maxOf { it.key.y }
        }

        fun withBeam(beam: Beam) = FloorPlanWithStartBeam(beam, this)
    }
    data class FloorPlanWithStartBeam(val startBeam: Beam, val floorMap: FloorMap) {
        private val fullySimulatedBeams by lazy {
            generateSequence(setOf(startBeam)) { last ->
                last + last.flatMap { x ->
                    val p = floorMap[x.point] ?: error("${x.point} is outside the map")
                    x.next(p).filter { newPoint ->
                        newPoint.point.x in floorMap.mapXBounds && newPoint.point.y in floorMap.mapYBounds
                    }
                }.toSet()
            }
                .zipWithNext()
                .first { (a, b) -> a == b }
                .first
        }
        val energizedTiles by lazy {
            fullySimulatedBeams.map { it.point }.distinct().size
        }
        fun prettyPrint() {
            floorMap.mapYBounds.forEach { y ->
                println(floorMap.mapXBounds.joinToString("") { x ->
                    when (floorMap[Point2D(x, y)]) {
                        '/' -> "/"
                        '|' -> "|"
                        '-' -> "-"
                        '\\' -> "\\"
                        else -> {
                            val res = fullySimulatedBeams.filter { it.point == Point2D(x, y) }
                            when {
                                res.isEmpty() -> '.'
                                res.size == 1 -> when (res.single().direction) {
                                    Point2D.RIGHT -> '>'
                                    Point2D.DOWN -> 'v'
                                    Point2D.LEFT -> '<'
                                    Point2D.UP -> '^'
                                    else -> error("invalid direction")
                                }

                                else -> res.size.toString().last()
                            }.toString()
                        }
                    }
                })
            }
        }
    }

    override fun part1(input: List<String>): Any {
        val start = Beam(Point2D(0, 0), Point2D.RIGHT)
        val map = input.flatMapIndexed { y, line ->
            line.mapIndexed { x, c -> Point2D(x, y) to c }
        }.associate { it }
        val floorMap = FloorMap(map).withBeam(start)
        // floorMap.prettyPrint()
        return floorMap.energizedTiles
    }

    override fun part2(input: List<String>): Any {
        val map = input.flatMapIndexed { y, line ->
            line.mapIndexed { x, c -> Point2D(x, y) to c }
        }.associate { it }
        val floorMap = FloorMap(map)
        val topStarts = floorMap.mapXBounds.map { Beam(Point2D(it, 0), Point2D.DOWN) }
        val leftStarts = floorMap.mapYBounds.map { Beam(Point2D(0, it), Point2D.RIGHT) }
        val rightStarts = floorMap.mapYBounds.map { Beam(Point2D(floorMap.mapXBounds.last, it), Point2D.LEFT) }
        val bottomStarts = floorMap.mapXBounds.map { Beam(Point2D(it, floorMap.mapYBounds.last), Point2D.UP) }
        return (topStarts + leftStarts + rightStarts + bottomStarts).maxOf { startBeam ->
            floorMap.withBeam(startBeam).energizedTiles
        }
    }
}

fun main() = Day16.run()
