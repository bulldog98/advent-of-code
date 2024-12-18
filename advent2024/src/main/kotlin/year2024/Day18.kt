package year2024

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import graph.AdjacencyListGraph
import graph.dijkstra
import helper.numbers.toAllLongs

class Day18(
    private val gridSize: Long,
    private val bytesForPart1: Int,
) : AdventDay(2024, 18) {
    private val start = Point2D.ORIGIN
    private val destination = Point2D(gridSize, gridSize)
    private val validCoordinates = start..destination

    private fun List<Point2D>.costForTravelToEndPoint(): Long {
        val emptySpaces = (0..gridSize).flatMap { x -> (0..gridSize).map { y -> Point2D(x, y) } }.filter {
            it !in this
        }
        val graph = AdjacencyListGraph(emptySpaces) { node ->
            node.cardinalNeighbors.filter { it in emptySpaces && it in validCoordinates }
        }
        val (cost) = graph.dijkstra(start) { _, _ -> 1L }
        return cost(destination) ?: Long.MAX_VALUE
    }

    override fun part1(input: InputRepresentation): Long {
        return input.map { it.toAllLongs().toList() }.map { Point2D(it[0], it[1]) }.take(bytesForPart1).costForTravelToEndPoint()
    }

    override fun part2(input: InputRepresentation): String {
        val bytesToCorrupt = input.map { it.toAllLongs().toList() }.map { Point2D(it[0], it[1]) }
        val indexOfBlockingByte = (bytesForPart1 ..< bytesToCorrupt.size).first {
            println("search with $it bytes corrupted")
            bytesToCorrupt.take(it).costForTravelToEndPoint() == Long.MAX_VALUE
        } - 1
        return bytesToCorrupt[indexOfBlockingByte].let {
            "${it.x},${it.y}"
        }
    }
}

fun main() = Day18(70, 1024).run()