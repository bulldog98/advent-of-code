package year2021

import AdventDay
import Point2D
import graph.AdjacencyListGraph
import graph.dijkstra

object Day15: AdventDay(2021, 15) {
    data class Field(val topLeft: Point2D, val bottomRight: Point2D) {
        constructor(bottomRight: Point2D): this(Point2D(0, 0), bottomRight)

        private val xRange by lazy {
            (topLeft.x..bottomRight.x)
        }
        private val yRange by lazy {
            (topLeft.y..bottomRight.y)
        }
        operator fun contains(point: Point2D): Boolean =
            point.x in xRange && point.y in yRange

        val points: Set<Point2D>
            get() = xRange.flatMap { x ->
                yRange.map { y ->
                    Point2D(x, y)
                }
            }.toSet()

        fun connectionFrom(point: Point2D): List<Point2D> =
            point.cardinalNeighbors.filter {
                it in this
            }
    }
    override fun part1(input: List<String>): Long {
        val bottomRight = Point2D(input[0].length - 1, input.size - 1)
        val field = Field(bottomRight)
        val graph = AdjacencyListGraph(
            field.points,
            field::connectionFrom
        )
        val (distance) = graph.dijkstra(Point2D(0, 0)) { _, to ->
            input[to.y.toInt()][to.x.toInt()].digitToInt().toLong()
        }
        return (distance(bottomRight) ?: 0L)
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day15.run()
