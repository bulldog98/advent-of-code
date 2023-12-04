package year2021

import AdventDay
import Point2D
import graph.AdjacencyListGraph
import graph.dijkstra
import year2021.day15.Field

object Day15 : AdventDay(2021, 15) {

    override fun part1(input: List<String>): Long {
        val field = Field.of(input)
        val (distance) = AdjacencyListGraph(
                field.keys,
                field::connectionFrom
            ).dijkstra(Point2D(0, 0)) { _, to ->
                (field[to] ?: 0).toLong()
            }
        return (distance(field.bottomRight) ?: 0L)
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day15.run()
