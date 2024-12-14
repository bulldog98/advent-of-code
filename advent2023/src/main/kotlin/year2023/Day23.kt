package year2023

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import findAllPositionsOf
import graph.AdjacencyListGraph
import graph.Graph

object Day23 : AdventDay(2023, 23) {
    private operator fun List<String>.get(point: Point2D): Char = this[point.y.toInt()][point.x.toInt()]

    private fun List<String>.buildGraph(ignoreSlops: Boolean = false): Graph<Point2D> =
        AdjacencyListGraph(
            findAllPositionsOf('.') + findAllPositionsOf('>') +
                findAllPositionsOf('<') + findAllPositionsOf('v') + findAllPositionsOf('^')
        ) { cur ->
            val bottomRight = Point2D(this[0].length - 1, this.size - 1)
            val leftOf = cur + Point2D.LEFT
            val rightOf = cur + Point2D.RIGHT
            val upOf = cur + Point2D.UP
            val downOf = cur + Point2D.DOWN
            val left = if (leftOf.x >= 0 && (this[leftOf] in ".<" || (ignoreSlops && this[leftOf] in ".<>v^")))
                listOf(leftOf)
            else
                emptyList()
            val right = if (rightOf.x <= bottomRight.x && (this[rightOf] in ".>" || (ignoreSlops && this[rightOf] in ".<>v^")))
                listOf(rightOf)
            else
                emptyList()
            val up = if (upOf.y >= 0 && (this[upOf] in ".^" || (ignoreSlops && this[upOf] in ".<>v^")))
                listOf(upOf)
            else
                emptyList()
            val down = if (downOf.y <= bottomRight.y && (this[downOf] in ".v"|| (ignoreSlops && this[downOf] in ".<>v^")))
                listOf(downOf)
            else
                emptyList()
            left + right + up + down
        }

    private fun Map<Point2D, List<Pair<Pair<Point2D, Point2D>, Int>>>.pathsFrom(
        point: Point2D,
        to: Point2D,
        seenPoints: Set<Point2D> = emptySet()
    ): List<Int> =
        this[point]!!.flatMap {
            val (p, cost) = it
            val (_, p2) = p
            when (p2) {
                in seenPoints -> emptyList()
                to -> listOf(cost)
                else -> pathsFrom(p2, to, seenPoints + p2).map { it + cost }
            }
        }

    private fun Graph<Point2D>.computeHelperMap(
        splits: List<Point2D>,
        start: Point2D,
        destination: Point2D,
        newNodes: List<Point2D>
    ) = (splits + start).associateWith { previous ->
        neighborsOf(previous).map {
            var cur = it
            val visited = mutableSetOf(cur, previous)
            do {
                val neighbors = neighborsOf(cur)
                cur = neighbors.single { n -> n !in visited || n == destination }
                visited += cur
            } while (cur !in newNodes)
            it to cur to visited.size - 1
        }
    }

    override fun part1(input: InputRepresentation): Int {
        val destination = Point2D(input[0].length - 2, input.size - 1)
        val start = Point2D(1, 0)
        val graph = input.buildGraph()

        val splits = graph.nodes.filter { graph.neighborsOf(it).filter { p -> input[p] in "^v<>" }.size == 2 }
        val help = graph.computeHelperMap(splits, start, destination, splits + start + destination)

        return help.pathsFrom(start, destination).max()
    }

    override fun part2(input: InputRepresentation): Any {
        val destination = Point2D(input[0].length - 2, input.size - 1)
        val start = Point2D(1, 0)
        val graph = input.buildGraph(true)

        val splits = graph.nodes.filter { graph.neighborsOf(it).filter { p -> input[p] in "^v<>" }.size >= 2 }
        val help = graph.computeHelperMap(splits, start, destination, splits + start + destination)

        return help.pathsFrom(start, destination).max()
    }
}

fun main() = Day23.run()
