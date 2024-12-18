package year2019

import adventday.AdventDay
import adventday.InputRepresentation
import graph.AdjacencyListGraph
import graph.Graph

object Day06: AdventDay(2019, 6) {
    // assume Graph has no cycles
    private fun Graph<String>.countOrbitsOf(
        node: String,
        countOfOrbits: Long = 0
    ): Long = when {
        neighborsOf(node).isEmpty() -> countOfOrbits
        else -> countOfOrbits + neighborsOf(node).sumOf { countOrbitsOf(it, countOfOrbits + 1) }
    }

    override fun part1(input: InputRepresentation): Long {
        val orbits = input.map {
            val (a, b) = it.split(")")
            a to b
        }
        val orbitMap = AdjacencyListGraph(input.flatMap { it.split("(") }.toSet()) { stellarObject ->
            orbits.filter { it.first == stellarObject }.map { it.second }
        }

        return orbitMap.countOrbitsOf("COM")
    }

    override fun part2(input: InputRepresentation): Long {
        TODO("Not yet implemented")
    }
}

fun main() = Day06.run()