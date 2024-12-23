package year2024

import adventday.AdventDay
import adventday.InputRepresentation
import collections.pairings
import graph.AdjacencyListGraph
import graph.enumerateAllMaxCliques

object Day23 : AdventDay(2024, 23) {
    private data class ComputerNetwork(val computers: List<String>, val directConnections: List<Set<String>>) {
        private val cache = mutableMapOf<Int, List<Set<String>>>()
        fun findDirectlyConnectedGroupOfSize(size: Int = 3): List<Set<String>> = cache.getOrPut(size) {
            if (size == 2)
                directConnections
            else
                findDirectlyConnectedGroupOfSize(size - 1)
                    .pairings()
                    .filter { (pairA, pairB) ->
                        val comp = pairA - pairB
                        comp.size == 1 && pairB.all { (comp + it) in directConnections }
                    }.map { (pairingA, pairingB) -> pairingA + pairingB }
                    .distinct()
                    .toList()
        }

        private val graph: AdjacencyListGraph<String> by lazy {
            AdjacencyListGraph(computers) { com ->
                directConnections.filter { com in it }.map { connection -> (connection - com).single() }
            }
        }

        fun findLargestDirectConnectedGroup(): Set<String> = graph.enumerateAllMaxCliques().maxBy { it.size }

        companion object {
            fun parse(input: InputRepresentation): ComputerNetwork {
                val connections = input.map { it.split("-").toSet() }
                val computers = connections.flatten().distinct()
                return ComputerNetwork(computers, connections)
            }
        }
    }

    override fun part1(input: InputRepresentation): Long = ComputerNetwork.parse(input)
        .findDirectlyConnectedGroupOfSize(3)
        .count { it.any { com -> com.startsWith('t') } }.toLong()

    override fun part2(input: InputRepresentation): String = ComputerNetwork.parse(input)
        .findLargestDirectConnectedGroup()
        .sorted()
        .joinToString(",")
}

fun main() = Day23.run()