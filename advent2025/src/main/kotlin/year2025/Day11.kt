package year2025

import NotYetImplemented
import adventday.AdventDay
import adventday.InputRepresentation
import graph.AdjacencyListGraph
import graph.Graph

object Day11 : AdventDay(2025, 11, "Reactor") {
    fun <T> Graph<T>.allPaths(from: T, to: T, visited: Set<T> = emptySet(), prefix: List<T> = emptyList()): List<List<T>> = when {
        from == to -> listOf(prefix)
        else -> neighborsOf(from).flatMap { newFrom -> allPaths(newFrom, to, visited + from, prefix + from) }
    }


    override fun part1(input: InputRepresentation): Int = input
        .lines
        .let { lines ->
            val nodes = lines.flatMap { it.split(": ", " ") }.distinct()
            val graph = AdjacencyListGraph(nodes) { node ->
                lines.first { it.startsWith(node) }.split(": ", " ").drop(1)
            }
            val paths = graph.allPaths("you", "out")
            paths.size
        }

    override fun part2(input: InputRepresentation): Any =
        NotYetImplemented
}

fun main() = Day11.run()
