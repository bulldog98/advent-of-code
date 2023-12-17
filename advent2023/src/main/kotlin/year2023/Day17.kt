package year2023

import AdventDay
import Point2D
import graph.AdjacencyListGraph
import graph.dijkstra

object Day17 : AdventDay(2023, 17) {
    data class CityBlocks(val input: List<String>) {
        val nodes = input.indices.flatMap { y ->
            input[y].indices.map { x -> Point2D(x, y) }
        }.toSet()
    }

    data class CrucibleTmpPosition(
        val position: Point2D,
        val direction: Point2D,
        val tilesMovedInDirection: Int = 0,
    )

    override fun part1(input: List<String>): Any {
        val cityBlocks = CityBlocks(input)
        val nodes = cityBlocks.nodes
            .flatMap {
                listOf(Point2D.LEFT, Point2D.RIGHT, Point2D.UP, Point2D.DOWN).flatMap { direction ->
                    (0..2).map { tilesMoved ->
                        CrucibleTmpPosition(it, direction, tilesMoved)
                    }
                }
            }.toSet()
        val graph = AdjacencyListGraph(nodes) { u ->
            val leftRight = when (u.direction) {
                Point2D.UP, Point2D.DOWN -> listOf(Point2D.LEFT, Point2D.RIGHT)
                Point2D.LEFT, Point2D.RIGHT -> listOf(Point2D.UP, Point2D.DOWN)
                else -> error("not a valid direction ${u.direction}")
            }
            val turnAround = leftRight
                .map { CrucibleTmpPosition(u.position + it, it) }
            when (u.tilesMovedInDirection) {
                2 -> turnAround.filter { it in nodes }
                else -> (
                    turnAround +
                        CrucibleTmpPosition(
                            position = u.position + u.direction,
                            u.direction,
                            u.tilesMovedInDirection + 1
                        )
                    ).filter { it in nodes }
            }
        }
        val (distance) = graph.dijkstra(
            CrucibleTmpPosition(Point2D(0, 0), Point2D.LEFT)
        ) { _, v ->
            val pos = v.position
            input[pos.y.toInt()][pos.x.toInt()].digitToInt().toLong()
        }
        val bottomRight = Point2D(input[0].indices.last, input.indices.last)
        return (0..2).flatMap { tiles ->
            listOf(Point2D.RIGHT, Point2D.DOWN).map { direction ->
                CrucibleTmpPosition(bottomRight, direction, tiles)
            }
        }.minOf { distance(it) ?: Long.MAX_VALUE }
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day17.run()
