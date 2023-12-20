package year2023

import AdventDay
import Point2D
import findAllPositionsOf
import kotlin.math.abs

object Day14 : AdventDay(2023, 14) {
    private fun Point2D.setCountInDirection(count: Int, direction: Point2D): List<Point2D> =
        (1..count).runningFold(this) { cur, _ ->
            cur + direction
        }.drop(1)

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
                val wallsInThisRow = (cubeRocks.filter { it.getRow() == coord }).sortingFunction(getColumn)
                val lookup = wallsInThisRow
                    .associateWith { wall ->
                        rocksInThisRow.count { it.getColumn() > wall.getColumn() }
                    }
                lookup.keys.toList().sortingFunction(getColumn)
                    .zipWithNext()
                    .associateWith { (a, b) -> abs(lookup[a]!! - lookup[b]!!) }
                    .filterValues { it != 0 }
                    .flatMap { (key, count) ->
                        val basePoint = listOf(key.first, key.second).sortingFunction(getColumn).last()
                        basePoint.setCountInDirection(count, direction)
                    }
            }.toSet()
            return copy(roundRocks = newRoundRockPositions)
        }


        fun tiltWithDirectionAsTop(direction: Point2D): Dish = when (direction) {
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

        @Suppress("Unused")
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
            .tiltWithDirectionAsTop(Point2D.DOWN)
//            .also { it.prettyPrint().forEach(::println) }
            .load

    override fun part2(input: List<String>): Long =
        generateSequence(Dish.of(input)) { dish ->
            dish
                .tiltWithDirectionAsTop(Point2D.DOWN)
                .tiltWithDirectionAsTop(Point2D.RIGHT)
                .tiltWithDirectionAsTop(Point2D.UP)
                .tiltWithDirectionAsTop(Point2D.LEFT)
        }
//            .also {
//                it.drop(1).take(3).forEach {
//                    it.prettyPrint().forEach(::println)
//                    println()
//                }
//            }
            .withIndex()
            .runningFold(emptyMap<Dish, List<Int>>()) { acc, cur ->
                acc + (cur.value to ((acc[cur.value] ?: emptyList()) + cur.index))
            }.first {
                it.any { (_, value) -> value.size > 2 }
            }.let { dishesToCycles ->
                val dishesAfterCycle = dishesToCycles.filterValues { it.size >= 2 }
                val (cycleStart, cycleLength) = dishesToCycles.values.first { it.size > 2 }.let { (a, b) ->
                    a to b - a
                }
                val firstEncounteredDishForOneBillion = ((1_000_000_000L - cycleStart) % cycleLength).toInt() + cycleStart
                dishesAfterCycle.entries.first { (_, value) -> value.any { it == firstEncounteredDishForOneBillion } }.key.load
            }
}

fun main() = Day14.run()
