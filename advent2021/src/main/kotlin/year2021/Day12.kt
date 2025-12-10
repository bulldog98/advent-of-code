package year2021

import adventday.AdventDay
import adventday.InputRepresentation
import graph.AdjacencyListGraph
import graph.Graph

object Day12 : AdventDay(2021, 12, "Passage Pathing") {
    private fun InputRepresentation.asGraph(): Graph<String> {
        val edges = lines.map { it: String ->
            it.split("-").toSet()
        }
        return AdjacencyListGraph(edges.flatten().distinct()) { v ->
            edges.filter { v in it }.flatMap { it - v }
        }
    }

    private fun InputRepresentation.countPathsFromStartToEnd(allowedToVisitSmallCaveAgain: Boolean = false): Long {
        val graph = asGraph()
        return graph.pathsFrom(PathFragment("start", graph.hashCode(), allowedToVisitSmallCaveAgain))
    }

    private data class PathFragment(
        val currentNode: String,
        val hash: Int,
        val allowedToVisitSmallCaveAgain: Boolean,
        val visited: Set<String> = setOf(currentNode)
    )

    private val cache = mutableMapOf<PathFragment, Long>()
    private fun Graph<String>.pathsFrom(from: PathFragment): Long =
        with(cache) {
            getOrPut(from) {
                when (from.currentNode) {
                    "end" -> 1
                    else -> (neighborsOf(from.currentNode) - "start").sumOf {
                        when {
                            it.all { char -> char.isUpperCase() } || it !in from.visited ->
                                pathsFrom(from.copy(currentNode = it, visited = from.visited + it))
                            it.all { char -> char.isLowerCase()  } && it in from.visited && !from.allowedToVisitSmallCaveAgain ->
                                0L
                            it.all { char -> char.isLowerCase()  } && it in from.visited && from.allowedToVisitSmallCaveAgain ->
                                pathsFrom(from.copy(currentNode = it, visited = from.visited + it, allowedToVisitSmallCaveAgain = false))
                            else ->
                                pathsFrom(from.copy(currentNode = it, visited = from.visited + it))
                        }
                    }
                }
            }
        }

    override fun part1(input: InputRepresentation): Long = input.countPathsFromStartToEnd()

    override fun part2(input: InputRepresentation): Long = input.countPathsFromStartToEnd(true)
}

fun main() = Day12.run()