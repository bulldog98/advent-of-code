package year2022

import adventday.AdventDay
import adventday.InputRepresentation
import graph.AdjacencyListGraph
import graph.Graph
import graph.dijkstra

private typealias Coordinate = Pair<Int, Int>
private typealias Terrain = Graph<Coordinate>

private fun List<String>.atPoint(point: Coordinate) = when (this[point.second][point.first]) {
    'S' -> 'a'
    'E' -> 'z'
    else -> this[point.second][point.first]
}

private fun Terrain(input: List<String>): Terrain {
    val nodes = input[0].indices.flatMap {  x ->
        (input.indices).map { y -> x to y }
    }
    val connections = buildMap {
        input[0].indices.forEach { x ->
            input.indices.forEach { y ->
                val ownHeight = input.atPoint(x to y)
                val reachableNeighbors = listOf(x - 1 to y, x + 1 to y, x to y - 1, x to y + 1).filter { (a, b) ->
                    a in input[0].indices && b in input.indices &&
                            ownHeight + 1 >= input.atPoint(a to b)
                }
                this[x to y] = reachableNeighbors
            }
        }
    }
    return AdjacencyListGraph(nodes, connections::getValue)
}

class Day12 : AdventDay(2022, 12) {
    private fun computeCostFunction(input: List<String>): (Coordinate, Coordinate) -> Long {
        return { u, v -> if (input.atPoint(u) + 1 >= input.atPoint(v)) 1 else Long.MAX_VALUE }
    }

    override fun part1(input: InputRepresentation): Long {
        val graph = Terrain(input)
        var from = -1 to -1
        var to = -1 to -1
        input[0].indices.forEach { x ->
            input.indices.forEach { y ->
                val c = input[y][x]
                if (c == 'S') {
                    from = x to y
                }
                if (c == 'E') {
                    to = x to y
                }
            }
        }
        val (dist, _) = graph.dijkstra(from, computeCostFunction(input))
        return dist(to) ?: Long.MAX_VALUE
    }

    override fun part2(input: InputRepresentation): Long {
        val graph = Terrain(input)
        var to = -1 to -1
        val startingPoints = mutableListOf<Pair<Int, Int>>()
        input[0].indices.forEach { x ->
            input.indices.forEach { y ->
                val c = input[y][x]
                if (c == 'a' || c == 'S') {
                    startingPoints += x to y
                }
                if (c == 'E') {
                    to = x to y
                }
            }
        }
        return startingPoints.fold(Long.MAX_VALUE) { curMin, from ->
            val (dist, _) = graph.dijkstra(from, computeCostFunction(input))
            listOf(curMin, dist(to) ?: Long.MAX_VALUE).min()
        }
    }
}

fun main() = Day12().run()