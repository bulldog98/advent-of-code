package year2024

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import findAllPositionsOf
import graph.AdjacencyListGraph
import graph.Graph
import graph.dijkstra

object Day16 : AdventDay(2024, 16) {
    private enum class DIRECTION(val vector: Point2D, val nextPointFor: (Point2D, Collection<Point2D>) -> Point2D?) {
        EAST(Point2D.RIGHT, { point, possible ->
            possible.filter { point.y == it.y && point.x < it.x }.minByOrNull { it.x }
        }),
        NORTH(Point2D.UP, { point, possible ->
            possible.filter { point.x == it.x && point.y > it.y }.maxByOrNull { it.y }
        }),
        WEST(Point2D.LEFT, { point, possible ->
            possible.filter { point.y == it.y && point.x > it.x }.maxByOrNull { it.x }
        }),
        SOUTH(Point2D.DOWN, { point, possible ->
            possible.filter { point.x == it.x && point.y < it.y }.minByOrNull { it.y }
        });

        fun rotateCounterClockwise() = when (this) {
            EAST -> NORTH
            NORTH -> WEST
            WEST -> SOUTH
            SOUTH -> EAST
        }

        fun rotateClockwise() = when (this) {
            EAST -> SOUTH
            NORTH -> EAST
            WEST -> NORTH
            SOUTH -> EAST
        }

        operator fun plus(point: Point2D) = vector + point
        operator fun minus(point: Point2D) = vector - point
    }

    private operator fun Point2D.plus(dir: DIRECTION) = this + dir.vector

    private class ReindeerMaze private constructor(
        val startPoint: Point2D,
        val finish: Point2D,
        val graphRepresentation: Graph<Pair<Point2D, DIRECTION>>
    ) : Graph<Pair<Point2D, DIRECTION>> by graphRepresentation {
        // only gives correct values if connection exists
        fun score(pointA: Pair<Point2D, DIRECTION>, pointB: Pair<Point2D, DIRECTION>) =
            if (pointA.second == pointB.second)
                pointA.first.manhattanDistance(pointB.first)
            else
                1000L

        companion object {
            fun parse(input: InputRepresentation): ReindeerMaze {
                val walls = input.findAllPositionsOf('#')
                val empty = input.findAllPositionsOf('.')
                val startPoint = input.findAllPositionsOf('S').first()
                val finish = input.findAllPositionsOf('E').first()
                val canWalkIn = empty + startPoint + finish
                fun Point2D.isCornerOrSplitOrDeadEnd(): Boolean =
                    cardinalNeighbors.count { it in canWalkIn } in setOf(1, 3, 4) ||
                        listOf(
                            listOf(Point2D.UP, Point2D.RIGHT),
                            listOf(Point2D.UP, Point2D.LEFT),
                            listOf(Point2D.DOWN, Point2D.RIGHT),
                            listOf(Point2D.DOWN, Point2D.LEFT),
                        ).any { it.all { p -> this + p in canWalkIn } }

                val nodePoints = canWalkIn.filter { it.isCornerOrSplitOrDeadEnd() }
                val nodes = nodePoints.flatMap {
                    DIRECTION.entries.map { dir -> it to dir }
                }
                val graph = AdjacencyListGraph(nodes) { (point, dir) ->
                    val allowedRotations = listOf(point to dir.rotateCounterClockwise(), point to dir.rotateClockwise())
                    val goFurther = if (point + dir in walls)
                        emptyList()
                    else
                        dir.nextPointFor(point, nodePoints)?.let { listOf(it to dir) } ?: emptyList()
                    goFurther + allowedRotations
                }
                return ReindeerMaze(
                    startPoint = startPoint,
                    finish = finish,
                    graphRepresentation = graph
                )
            }
        }
    }

    override fun part1(input: InputRepresentation): Long {
        val reindeerMaze = ReindeerMaze.parse(input)
        val endPoints = DIRECTION.entries.map { dir -> reindeerMaze.finish to dir }.filter { it in reindeerMaze.nodes }
        return endPoints.minOf {
            val (cost) = reindeerMaze.dijkstra(reindeerMaze.startPoint to DIRECTION.EAST, reindeerMaze::score)
            cost(it) ?: Long.MAX_VALUE
        }
    }

    override fun part2(input: InputRepresentation): Long {
        TODO("Not yet implemented")
    }
}

fun main() = Day16.run()