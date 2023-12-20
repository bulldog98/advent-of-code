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

        private fun LongRange.tiltWithRow(
            direction: Point2D,
            getRow: Point2D.() -> Long,
            getColumn: Point2D.() -> Long,
            sortingFunction: List<Point2D>.(Point2D.() -> Long) -> List<Point2D>
        ): Dish {
            val newRoundRockPositions: Set<Point2D> = this.flatMap { coord ->
                val rocksInThisRow = roundRocks.filter { it.getRow() == coord }.sortedBy(getColumn)
                val wallsInThisRow = (cubeRocks.filter { it.getRow() == coord }).sortedByDescending(getColumn)
                val lookup = wallsInThisRow
                    .associateWith { wall ->
                        rocksInThisRow.count { it.getColumn() > wall.getColumn() }
                    }
                wallsInThisRow.zipWithNext().filter { (a, b) ->
                    lookup[a] != lookup[b]
                }.flatMap { (oldPoint, point) ->
                    point.setCountInDirection(lookup[point]!! - lookup[oldPoint]!!, direction)
                }
            }.toSet()
            return copy(roundRocks = newRoundRockPositions)
        }


        fun tiltInDirection(direction: Point2D): Dish = when (direction) {
            Point2D.UP, Point2D.DOWN -> xRange.tiltWithRow(direction, Point2D::x, Point2D::y) {
                if (direction == Point2D.DOWN) {
                    sortedByDescending(it)
                } else
                    sortedBy(it)
            }
            Point2D.LEFT, Point2D.RIGHT -> yRange.tiltWithRow(direction, Point2D::y, Point2D::x) {
                if (direction == Point2D.RIGHT) {
                    sortedByDescending(it)
                } else
                    sortedBy(it)
            }
            else -> error("that is not one of cardinal directions")
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
                ).let {
                    it.copy(
                        cubeRocks = it.cubeRocks
                            + it.yRange.flatMap { y ->
                            listOf(-1, it.xRange.last + 1)
                                .map { x -> Point2D(x, y) }
                        } + it.xRange.flatMap { x ->
                            listOf(-1, it.yRange.last + 1)
                                .map { y -> Point2D(x, y) }
                        }
                    )
                }
        }
    }

    override fun part1(input: List<String>): Long =
        Dish.of(input)
            .tiltInDirection(Point2D.DOWN)
            .also { it.prettyPrint().forEach(::println) }
            .load

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day14.run()
