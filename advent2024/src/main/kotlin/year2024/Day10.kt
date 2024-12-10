package year2024

import AdventDay
import Point2D
import findAllPositionsOf
import graph.AdjacencyListGraph
import graph.Graph
import graph.reachableNodesFrom

fun List<String>.allPositions() = this[0].indices.flatMap { x ->
    this.indices.map { y -> Point2D(x, y) }
}

operator fun List<String>.get(point2D: Point2D) = this[point2D.y.toInt()][point2D.x.toInt()]

fun List<String>.computeGraph(xRange: IntRange, yRange: IntRange) = AdjacencyListGraph(this.allPositions()) {
    val start = this[it]
    it.cardinalNeighbors.filter { neighbor ->
        neighbor.x in xRange && neighbor.y in yRange &&
            start.digitToInt() + 1 == this[neighbor].digitToInt()
    }
}

fun Graph<Point2D>.computeScoreFor(start: Point2D, isEndPoint: (Point2D) -> Boolean) = reachableNodesFrom(start)
    .count { reached -> isEndPoint(reached) }.toLong().also { res -> println("$start reaches $res 9s") }

fun Graph<Point2D>.computeRatingFor(start: Point2D, isEndPoint: (Point2D) -> Boolean) = buildList<List<Point2D>> {
    add(listOf(start))
    while (this.any { neighborsOf(it.last()).isNotEmpty() }) {
        val way = this.first { neighborsOf(it.last()).isNotEmpty() }
        this -= way
        this.addAll(neighborsOf(way.last()).map { way + it })
    }
}.count { isEndPoint(it.last()) }

object Day10 : AdventDay(2024, 10) {
    override fun part1(input: List<String>): Long {
        val startPoints = input.findAllPositionsOf('0')
        val xRange = input[0].indices
        val yRange = input.indices
        val graph = input.computeGraph(xRange, yRange)
        return startPoints.sumOf { graph.computeScoreFor(it) { end -> input[end] == '9' } }
    }

    override fun part2(input: List<String>): Long {
        val startPoints = input.findAllPositionsOf('0')
        val xRange = input[0].indices
        val yRange = input.indices
        val graph = input.computeGraph(xRange, yRange)
        return startPoints.sumOf { start -> graph.computeRatingFor(start) { end -> input[end] == '9' }.toLong() }
    }
}

fun main() = Day10.run()