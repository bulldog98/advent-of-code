package year2019

import adventday.AdventDay
import adventday.InputRepresentation
import graph.AdjacencyListGraph
import graph.Graph
import graph.dijkstra

object Day06: AdventDay(2019, 6, "Universal Orbit Map") {
    // assume Graph has no cycles
    private fun Graph<String>.countOrbitsOf(
        node: String,
        countOfOrbits: Long = 0
    ): Long = when {
        neighborsOf(node).isEmpty() -> countOfOrbits
        else -> countOfOrbits + neighborsOf(node).sumOf { countOrbitsOf(it, countOfOrbits + 1) }
    }

    override fun part1(input: InputRepresentation): Long {
        val orbits = input.lines.map { it: String ->
            val (a, b) = it.split(")")
            a to b
        }
        val orbitMap = AdjacencyListGraph(input.lines.flatMap { it.split("(") }.toSet()) { stellarObject ->
            orbits.filter { it.first == stellarObject }.map { it.second }
        }

        return orbitMap.countOrbitsOf("COM")
    }

    override fun part2(input: InputRepresentation): Long {
        val orbits = input.lines.map { it: String ->
            val (a, b) = it.split(")")
            a to b
        }
        val orbitsWithoutSantaAndYou = orbits.filter { it.second != "SAN" && it.second != "YOU" }
        val youAreOrbiting = orbits.first { it.second == "YOU" }.first
        val santaOrbits = orbits.first { it.second == "SAN" }.first
        val orbitMap = AdjacencyListGraph(orbitsWithoutSantaAndYou.flatMap { listOf(it.first, it.second) }.toSet()) { stellarObject ->
            orbitsWithoutSantaAndYou.filter { it.first == stellarObject }.map { it.second } +
                orbitsWithoutSantaAndYou.filter { it.second == stellarObject }.map { it.first }
        }
        val (distance) = orbitMap.dijkstra(youAreOrbiting) { _,_ -> 1 }
        return distance(santaOrbits) ?: Long.MAX_VALUE
    }
}

fun main() = Day06.run()