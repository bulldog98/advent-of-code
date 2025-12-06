package year2024

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import findAllPositionsOf
import graph.AdjacencyListGraph
import graph.Graph
import graph.dijkstra
import year2024.Day16.plus

object Day16 : AdventDay(2024, 16, "Reindeer Maze") {
    private enum class DIRECTION(val vector: Point2D, val nextPointFor: (Point2D, Collection<Point2D>) -> Point2D?) {
        WEST(Point2D.LEFT, { point, possible ->
            possible.filter { point.y == it.y && point.x > it.x }.maxByOrNull { it.x }
        }),
        NORTH(Point2D.UP, { point, possible ->
            possible.filter { point.x == it.x && point.y > it.y }.maxByOrNull { it.y }
        }),
        EAST(Point2D.RIGHT, { point, possible ->
            possible.filter { point.y == it.y && point.x < it.x }.minByOrNull { it.x }
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
        val cornerSplitOrDeadEnd: Collection<Point2D>,
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
                val walls = input.lines.findAllPositionsOf('#')
                val empty = input.lines.findAllPositionsOf('.')
                val startPoint = input.lines.findAllPositionsOf('S').first()
                val finish = input.lines.findAllPositionsOf('E').first()
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
                    cornerSplitOrDeadEnd = nodePoints,
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

    private data class PartialWay(
        val node: Pair<Point2D, DIRECTION>,
        val way: List<Pair<Point2D, DIRECTION>> = listOf(node),
    ) {
        fun addNextNode(node: Pair<Point2D, DIRECTION>) = copy(
            node = node,
            way = way + node
        )
    }

    override fun part2(input: InputRepresentation): Long {
        val reindeerMaze = ReindeerMaze.parse(input)
        val endPoints = DIRECTION.entries.map { dir -> reindeerMaze.finish to dir }.filter { it in reindeerMaze.nodes }
        val (costBacking) = reindeerMaze.dijkstra(
            reindeerMaze.startPoint to DIRECTION.EAST,
            reindeerMaze::score
        )

        fun cost(node: Pair<Point2D, DIRECTION>) =
            if (node.first == reindeerMaze.startPoint && node.second == DIRECTION.EAST)
                0L
            else
                costBacking(node) ?: Long.MAX_VALUE

        val maxAllowedCost = endPoints.minOf { cost(it) }
        val foundWays = buildSet {
            val frontier = mutableSetOf(PartialWay(reindeerMaze.startPoint to DIRECTION.EAST))
            while (frontier.isNotEmpty()) {
                val currentFrontierToMove = frontier.toMutableList().removeFirst()
                frontier -= currentFrontierToMove
                val (currentPos, currentDir) = currentFrontierToMove.node
                if (currentPos == reindeerMaze.finish) {
                    add(currentFrontierToMove.way)
                    continue
                }
                if (input[currentPos + currentDir] in setOf('.', 'S', 'E')) {
                    val next = currentDir.nextPointFor(currentPos, reindeerMaze.cornerSplitOrDeadEnd)
                    if (next != null) {
                        val nextNode = next to currentDir
                        if (cost(nextNode) <= maxAllowedCost && cost(nextNode) > cost(currentFrontierToMove.node) && nextNode !in currentFrontierToMove.way) {
                            frontier += currentFrontierToMove.addNextNode(nextNode)
                        }
                    }
                }
                frontier += listOf(currentDir.rotateClockwise(), currentDir.rotateCounterClockwise()).map {
                    currentPos to it
                }
                    .filter { cost(it) <= maxAllowedCost && cost(it) > cost(currentFrontierToMove.node) && it !in currentFrontierToMove.way }
                    .map { currentFrontierToMove.addNextNode(it) }
            }
        }
        return buildSet {
            foundWays.forEach { way ->
                way.zipWithNext().forEach { (node1, node2) ->
                    val (pos1, dir1) = node1
                    val (pos2) = node2
                    if (pos1 == pos2) {
                        add(pos1)
                    }
                    var curPos = pos1
                    while (curPos != pos2) {
                        curPos += dir1
                        add(curPos)
                    }
                }
            }
        }.size.toLong()
    }
}

fun main() = Day16.run()