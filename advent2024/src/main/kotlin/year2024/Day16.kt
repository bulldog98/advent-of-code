package year2024

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import findAllPositionsOf
import graph.AdjacencyListGraph
import graph.dijkstra

object Day16 : AdventDay(2024, 16) {
    private val directions = listOf(Point2D.UP, Point2D.DOWN, Point2D.RIGHT, Point2D.LEFT)

    private fun Point2D.rotateCounterClockwise() = when (this) {
        Point2D.UP -> Point2D.LEFT
        Point2D.LEFT -> Point2D.DOWN
        Point2D.DOWN -> Point2D.RIGHT
        Point2D.RIGHT -> Point2D.UP
        else -> error("not implemented")
    }

    private fun Point2D.rotateClockwise() = when (this) {
        Point2D.UP -> Point2D.RIGHT
        Point2D.LEFT -> Point2D.UP
        Point2D.DOWN -> Point2D.LEFT
        Point2D.RIGHT -> Point2D.DOWN
        else -> error("not implemented")
    }

    override fun part1(input: InputRepresentation): Long {
        val empty = input.findAllPositionsOf('.')
        val wall = input.findAllPositionsOf('#')
        val startPoint = input.findAllPositionsOf('S').first()
        val finish = input.findAllPositionsOf('E').first()
        val graph = AdjacencyListGraph(
            (empty + startPoint + finish).flatMap { directions.map { dir -> it to dir } }
        ) { (point, dir) ->
            val goFurther = if (point + dir in wall)
                emptyList()
            else
                listOf(point + dir to dir)
            goFurther + listOf(point to dir.rotateCounterClockwise(), point to dir.rotateClockwise())
        }
        val endPoints = directions.map { dir -> finish to dir }
        return endPoints.minOf {
            val (cost) = graph.dijkstra(startPoint to Point2D.RIGHT) { (_, dir1), (_, dir2) ->
                if (dir1 == dir2)
                    1L
                else
                    1000L
            }
            cost(it) ?: Long.MAX_VALUE
        }
    }

    override fun part2(input: InputRepresentation): Long {
        TODO("Not yet implemented")
    }
}

fun main() = Day16.run()