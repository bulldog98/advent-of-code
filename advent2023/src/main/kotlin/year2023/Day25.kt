package year2023

import adventday.AdventDay
import adventday.InputRepresentation
import graph.AdjacencyListGraph
import graph.dijkstra
import java.nio.file.Files
import java.nio.file.Path

object Day25 : AdventDay(2023, 25) {
    override fun part1(input: InputRepresentation): Int {
        val nodes = input.flatMap { it.split(" ", ": ") }.toSet()
        val connections = nodes.associateWith { n ->
            input.filter { n in it }.flatMap {
                val (first, second) = it.split(": ")
                if (n == first) second.split(" ")
                else listOf(first)
            }
        }
        val edges = connections.flatMap { (key, value) -> value.map { setOf(it, key) } }.toSet().toList()
        // this uses graphviz to visualize the graph
        Files.newOutputStream(Path.of("./Day25_result.txt")).writer().use { out ->
            out.write("graph {${System.lineSeparator()}")
            edges.forEach {
                val (a, b) = it.toList()
                out.write("""$a -- $b;${System.lineSeparator()}""")
            }
            out.write("}${System.lineSeparator()}")
        }
        // after use `dot Day25_result.txt -Tsvg -Kneato > out.svg` and look at the out.svg to see the edges, that split
        val edgesThatSplit = listOf(
            setOf("qdv", "zhg"),
            setOf("lsk", "rfq"),
            setOf("gpz", "prk"),
        )
        edgesThatSplit.indices.forEach { i ->
            edgesThatSplit.indices.forEach { j ->
                edgesThatSplit.indices.forEach { z ->
                    if (i != j && j != z && i != z) {
                        val restEdges = edgesThatSplit.filterIndexed { v, _ -> v in setOf(i, j, z) }
                        val graph = AdjacencyListGraph(nodes) { node ->
                            edges.filter { node in it && it !in restEdges }
                                .flatMap { it.filter { n -> n != node } }
                        }
                        val (d) = graph.dijkstra(edgesThatSplit[i].toList()[0]) { _, _ -> 1L }
                        val (a, b) = nodes.partition { d(it) != null }
                        if (a.isNotEmpty() && a.size != 1 && b.isNotEmpty() && b.size != 1) {
                            return (a.size + 1) * (b.size - 1) // since start node of dijkstra has also d == null
                        }
                    }
                }
            }
        }
        error("found no solution")
    }

    override fun part2(input: InputRepresentation): Int = 1 // nothing to do only click on button
}

fun main() = Day25.run()
