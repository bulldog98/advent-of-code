package year2023

import AdventDay
import Point2D
import graph.AdjacencyListGraph
import graph.dijkstra
import kotlin.math.max
import kotlin.math.min

object Day17 : AdventDay(2023, 17) {
    data class CityBlocks(val input: List<String>) {
        val nodes = input.indices.flatMap { y ->
            input[y].indices.map { x -> Point2D(x, y) }
        }.toSet()
    }

    private operator fun Point2D.times(factor: Int): Point2D =
        Point2D(x * factor, y * factor)

    data class CrucibleTmpPosition(
        val position: Point2D,
        val direction: Point2D,
    ) {
        fun makeOneMove(): List<CrucibleTmpPosition> = when (direction) {
            Point2D.UP, Point2D.DOWN -> listOf(Point2D.LEFT, Point2D.RIGHT).flatMap { newDir ->
                (0..2).map {
                    CrucibleTmpPosition(position + direction * it + newDir, newDir)
                }
            }
            Point2D.LEFT, Point2D.RIGHT -> listOf(Point2D.UP, Point2D.DOWN).flatMap { newDir ->
                (0..2).map {
                    CrucibleTmpPosition(position + direction * it + newDir, newDir)
                }
            }
            else -> error("invalid direction $direction")
        }
    }

    operator fun List<String>.get(point: Point2D): Long? =
        getOrNull(point.y.toInt())
            ?.getOrNull(point.x.toInt())
            ?.digitToInt()
            ?.toLong()

    operator fun List<String>.get(cruciblePosition: CrucibleTmpPosition): Long? =
        this[cruciblePosition.position]

    override fun part1(input: List<String>): Long {
        val cityBlocks = CityBlocks(input)
        val nodes = cityBlocks.nodes
            .flatMap {
                listOf(Point2D.LEFT, Point2D.RIGHT, Point2D.UP, Point2D.DOWN)
                    .map { direction ->
                        CrucibleTmpPosition(it, direction)
                    }
            }.toSet()
        val graph = AdjacencyListGraph(nodes) { u ->
            u.makeOneMove().filter { it in nodes }
        }
        val (distance) = graph.dijkstra(
            CrucibleTmpPosition(Point2D(0, 0), Point2D.RIGHT)
        ) { u, v ->
            val costForVItself = input[v] ?: error("v $v has to be in input")
            val costForUItself = input[u] ?: error("u $u has to be in input")
            return@dijkstra costForVItself - costForUItself + when (u.direction) {
                Point2D.LEFT, Point2D.RIGHT -> (min(v.position.x, u.position.x)..max(v.position.x, u.position.x)).sumOf { x ->
                    input[u.position.y.toInt()][x.toInt()].digitToInt().toLong()
                }
                Point2D.UP, Point2D.DOWN -> (min(v.position.y, u.position.y) ..max(v.position.y, u.position.y)).sumOf { y ->
                    input[y.toInt()][u.position.x.toInt()].digitToInt().toLong()
                }
                else -> error("invalid direction ${u.direction}")
            }
        }
        val bottomRight = Point2D(input[0].indices.last, input.indices.last)
        return listOf(Point2D.RIGHT, Point2D.DOWN).map { direction ->
                CrucibleTmpPosition(bottomRight, direction)
            }.minOf { distance(it) ?: Long.MAX_VALUE }
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day17.run()
