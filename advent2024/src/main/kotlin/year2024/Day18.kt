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
    override fun part1(input: InputRepresentation): Long {
        val corruptedBytes = input.map { it.toAllLongs().toList() }.map { Point2D(it[0], it[1]) }.take(bytesForPart1).toSet()
        val start = Point2D.ORIGIN
        val destination = Point2D(gridSize, gridSize)
        val validCoordinates = start..destination
        val emptySpaces = (0..gridSize).flatMap { x -> (0..gridSize).map { y -> Point2D(x, y) } }.filter {
            it !in corruptedBytes
        }
//        (0 .. gridSize).forEach { y ->
//            val line = (0..gridSize).joinToString("") { x ->
//                if (Point2D(x, y) in corruptedBytes) "#" else "."
//            }
//            println(line)
//        }
        val graph = AdjacencyListGraph(emptySpaces) { node ->
            node.cardinalNeighbors.filter { it in emptySpaces && it in validCoordinates }
        }
        val (cost) = graph.dijkstra(start) { _, _ -> 1L }
        return cost(destination) ?: Long.MAX_VALUE
    }

    override fun part2(input: InputRepresentation): Long {
        TODO("Not yet implemented")
    }
}

fun main() = Day18(70, 1024).run()