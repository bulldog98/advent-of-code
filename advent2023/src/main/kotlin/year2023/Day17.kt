package year2023

import AdventDay
import Point2D
import graph.AdjacencyListGraph
import graph.Graph
import graph.dijkstra

object Day17 : AdventDay(2023, 17) {
    data class CityBlocks(val input: List<String>) {
        private val nodes = input.indices.flatMap { y ->
            input[y].indices.map { x -> Point2D(x, y) }
        }.toSet()

        fun buildGraph(min: Int, max: Int): Graph<CrucibleTmpPosition> {
            val realNodes = this.nodes
                .flatMap {
                    listOf(Point2D.LEFT, Point2D.RIGHT, Point2D.UP, Point2D.DOWN).flatMap { direction ->
                        (1 ..max).map { tilesMoved ->
                            CrucibleTmpPosition(it, direction, tilesMoved)
                        }
                    }.filter { it.position in nodes }
                }.toSet()
            return AdjacencyListGraph(realNodes) { u ->
                val leftRight = when (u.direction) {
                    Point2D.UP, Point2D.DOWN -> listOf(Point2D.LEFT, Point2D.RIGHT)
                    Point2D.LEFT, Point2D.RIGHT -> listOf(Point2D.UP, Point2D.DOWN)
                    else -> error("not a valid direction ${u.direction}")
                }
                val turnAround = leftRight
                    .map { CrucibleTmpPosition(u.position + it, it) }
                when {
                    u.tilesMovedInDirection == max -> turnAround
                    u.tilesMovedInDirection < min ->
                        listOf(
                            CrucibleTmpPosition(
                                position = u.position + u.direction,
                                u.direction,
                                u.tilesMovedInDirection + 1
                            )
                        )
                    else ->
                        turnAround +
                            CrucibleTmpPosition(
                                position = u.position + u.direction,
                                u.direction,
                                u.tilesMovedInDirection + 1
                            )
                }.filter { it.position in nodes }
            }
        }
    }

    private operator fun List<String>.get(point: Point2D): Long? =
        getOrNull(point.y.toInt())?.getOrNull(point.x.toInt())?.digitToInt()?.toLong()

    data class CrucibleTmpPosition(
        val position: Point2D,
        val direction: Point2D,
        val tilesMovedInDirection: Int = 1,
    )

    override fun part1(input: List<String>): Any {
        val cityBlocks = CityBlocks(input)
        val graph = cityBlocks.buildGraph(1, 3)
        val (distance) = graph.dijkstra(
            CrucibleTmpPosition(Point2D(0, 0), Point2D.RIGHT)
        ) { _, v ->
            input[v.position]!!
        }
        val bottomRight = Point2D(input[0].indices.last, input.indices.last)
        return (1..3).flatMap { tiles ->
            listOf(Point2D.RIGHT, Point2D.DOWN).map { direction ->
                CrucibleTmpPosition(bottomRight, direction, tiles)
            }
        }.minOf { distance(it) ?: Long.MAX_VALUE }
    }

    override fun part2(input: List<String>): Any {
        val cityBlocks = CityBlocks(input)
        val graph = cityBlocks.buildGraph(4, 10)
        val (distance) = graph.dijkstra(
            CrucibleTmpPosition(Point2D(0, 0), Point2D.RIGHT)
        ) { _, v ->
            input[v.position]!!
        }
        val bottomRight = Point2D(input[0].indices.last, input.indices.last)
        return (4..10).flatMap { tiles ->
            listOf(Point2D.RIGHT, Point2D.DOWN).map { direction ->
                CrucibleTmpPosition(bottomRight, direction, tiles)
            }
        }.minOf { distance(it) ?: Long.MAX_VALUE }
    }
}

fun main() = Day17.run()
