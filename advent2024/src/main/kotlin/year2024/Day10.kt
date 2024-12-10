package year2024

import AdventDay
import Point2D
import findAllPositionsOf
import graph.AdjacencyListGraph
import graph.reachableNodesFrom

fun List<String>.allPositions() = this[0].indices.flatMap { x ->
    this.indices.map { y -> Point2D(x, y) }
}

operator fun List<String>.get(point2D: Point2D) = this[point2D.y.toInt()][point2D.x.toInt()]

object Day10 : AdventDay(2024, 10) {
    override fun part1(input: List<String>): Long {
        val startPoints = input.findAllPositionsOf('0')
        val xRange = input[0].indices
        val yRange = input.indices
        val graph = AdjacencyListGraph(input.allPositions()) {
            val start = input[it]
            it.cardinalNeighbors.filter { neighbor ->
                neighbor.x in xRange && neighbor.y in yRange &&
                    start.digitToInt() + 1 == input[neighbor].digitToInt()
            }
        }
        return startPoints.sumOf { graph.reachableNodesFrom(it).count { reached -> input[reached] == '9' }.toLong().also { res -> println("$it reaches $res 9s") } }
    }

    override fun part2(input: List<String>): Long {
        TODO("Not yet implemented")
    }
}

fun main() = Day10.run()