package year2023

import AdventDay
import Point2D
import findAllPositionsOf

object Day14 : AdventDay(2023, 14) {
    private fun Point2D.setCountInDirection(count: Int, direction: Point2D): List<Point2D> =
        (1..count).runningFold(this) { cur, _ ->
            cur + direction
        }.drop(1)

    data object RoundRock
    data object CubeRock
    data class Dish(
        val xRange: LongRange,
        val yRange: LongRange,
        val cubeRocks: Set<Point2D>,
        val roundRocks: Set<Point2D>
    ) {
        val load: Long
            get() = roundRocks.sumOf { yRange.last - it.y + 1 }

        fun tiltNorth(): Dish {
            val newRoundRockPositions: Set<Point2D> = xRange.flatMap { x ->
                val rocksInThisRow = roundRocks.filter { it.x == x }.sortedBy { it.y }
                val wallsInThisRow = (cubeRocks.filter { it.x == x } + Point2D(x, -1) + Point2D(x, yRange.last + 1)).sortedByDescending { it.y }
                if (wallsInThisRow.size == 1)
                     Point2D(x, -1).setCountInDirection(rocksInThisRow.size, Point2D.DOWN)
                else {
                    val lookup = wallsInThisRow
                        .associateWith { wall ->
                            rocksInThisRow.count { it.y > wall.y }
                        }
                    wallsInThisRow.zipWithNext().filter { (a, b) ->
                        lookup[a] != lookup[b]
                    }.flatMap { (oldPoint, point) ->
                        point.setCountInDirection(lookup[point]!! - lookup[oldPoint]!!, Point2D.DOWN)
                    }
                }
            }.toSet()
            return copy(roundRocks = newRoundRockPositions)
        }

        fun prettyPrint(): List<String> = yRange.map { y ->
            xRange.joinToString("") { x ->
                when (Point2D(x, y)) {
                    in cubeRocks -> "#"
                    in roundRocks -> "O"
                    else -> "."
                }
            }
        }

        companion object {
            fun of(input: List<String>): Dish =
                Dish(
                    0L..<input[0].length,
                    0L..<input.size.toLong(),
                    input.findAllPositionsOf('#'),
                    input.findAllPositionsOf('O')
                )
        }
    }

    override fun part1(input: List<String>): Long =
        Dish.of(input)
            .tiltNorth()
            // .also { it.prettyPrint().forEach(::println) }
            .load

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day14.run()
