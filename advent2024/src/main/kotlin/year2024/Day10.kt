package year2024

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import findAllPositionsOf
import graph.AdjacencyListGraph
import graph.Graph
import graph.reachableNodesFrom

data class HikeMap(private val input: List<String>) {
    private val startPoints = input.findAllPositionsOf('0')
    private val xRange = input[0].indices
    private val yRange = input.indices
    private val graph = input.computeGraph(xRange, yRange)

    private fun List<String>.allPositions() = this[0].indices.flatMap { x ->
        this.indices.map { y -> Point2D(x, y) }
    }

    private fun List<String>.computeGraph(xRange: IntRange, yRange: IntRange) = AdjacencyListGraph(this.allPositions()) {
        val start = this[it]
        it.cardinalNeighbors.filter { neighbor ->
            neighbor.x in xRange && neighbor.y in yRange &&
                start.digitToInt() + 1 == this[neighbor].digitToInt()
        }
    }

    private operator fun List<String>.get(point2D: Point2D) = this[point2D.y.toInt()][point2D.x.toInt()]

    private fun Graph<Point2D>.computeRatingFor(start: Point2D) = buildList<List<Point2D>> {
        add(listOf(start))
        while (this.any { neighborsOf(it.last()).isNotEmpty() }) {
            val way = this.first { neighborsOf(it.last()).isNotEmpty() }
            this -= way
            this.addAll(neighborsOf(way.last()).map { way + it })
        }
    }.count { input[it.last()] == '9' }

    fun computeRating() = startPoints.sumOf { graph.computeRatingFor(it) }

    private fun Graph<Point2D>.computeScoreFor(start: Point2D) = reachableNodesFrom(start)
        .count { input[it] == '9' }

    fun computeScore() = startPoints.sumOf { graph.computeScoreFor(it) }
}


object Day10 : AdventDay(2024, 10) {
    override fun part1(input: InputRepresentation) = HikeMap(input.lines).computeScore()

    override fun part2(input: InputRepresentation) = HikeMap(input.lines).computeRating()
}

fun main() = Day10.run()