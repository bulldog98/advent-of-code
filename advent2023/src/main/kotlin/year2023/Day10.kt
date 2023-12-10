package year2023

import AdventDay
import Point2D
import graph.AdjacencyListGraph
import graph.dijkstra

object Day10: AdventDay(2023, 10) {
    fun Char.isConnectedTo(point2D: Point2D): Boolean = when (this) {
        '|' -> point2D == Point2D.UP || point2D == Point2D.DOWN
        '-' -> point2D == Point2D.LEFT || point2D == Point2D.RIGHT
        'L' -> point2D == Point2D.UP || point2D == Point2D.RIGHT
        'J' -> point2D == Point2D.UP || point2D == Point2D.LEFT
        '7' -> point2D == Point2D.DOWN || point2D == Point2D.LEFT
        'F' -> point2D == Point2D.DOWN || point2D == Point2D.RIGHT
        'S' -> true
        else -> false
    }
    data class Field(val pipes: Map<Point2D, Char>) {
        companion object {
            private fun ofHelper(input: List<String>): Map<Point2D, Char> = buildMap {
                input.indices.forEach { y ->
                    (input[y].indices).forEach { x ->
                        if (input[y][x] != '.') {
                            this += Point2D(x, y) to input[y][x]
                        }
                    }
                }
            }
            fun of(input: List<String>): Field = Field(ofHelper(input))

        }
    }
    override fun part1(input: List<String>): Any {
        val field = Field.of(input)
        val start = field.pipes.filterValues { it == 'S' }.keys.firstOrNull() ?: error("no start position found")
        val distanceMap = mutableMapOf(start to 0)
        val graph = AdjacencyListGraph(field.pipes.keys) { pos ->
            pos.cardinalNeighbors.filter { field.pipes.getOrDefault(it, '.').isConnectedTo(pos - it) }
        }
        val (distance, _) = graph.dijkstra(start) { _, _ -> 1 }
        return field.pipes.keys.maxOf { distance(it) ?: Long.MIN_VALUE }
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day10.run()
