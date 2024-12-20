package year2024

import adventday.AdventDay
import adventday.InputRepresentation
import findAllPositionsOf
import graph.AdjacencyListGraph
import graph.dijkstra

object Day20 : AdventDay(2024, 20) {
    override fun part1(input: InputRepresentation): Int {
        val walls = input.findAllPositionsOf('#')
        val start = input.findAllPositionsOf('S').single()
        val finish = input.findAllPositionsOf('E').single()
        val normal = input.findAllPositionsOf('.')
        val walkablePlaces = normal + start + finish
        val normalMapGraph = AdjacencyListGraph(walkablePlaces) { node ->
            node.cardinalNeighbors.filter { it !in walls }
        }
        val (_, predecessor) = normalMapGraph.dijkstra(start) { _, _ -> 1 }
        val way = generateSequence(finish) { predecessor(it) }.toList().reversed()
        val cheats = way.drop(2).flatMapIndexed { start, cheatStart ->
            way.subList(start + 2, way.size).filter { cheatEnd ->
                cheatStart.manhattanDistance(cheatEnd) == 2L &&
                    cheatStart.cardinalNeighbors.count { it in cheatEnd.cardinalNeighbors && it in walls } > 0
            }.map { cheatStart to it }
        }
        return cheats.map { (a, b) -> way.indexOf(b) - way.indexOf(a) - 2 }.count { it >= 100 }
    }

    override fun part2(input: InputRepresentation): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day20.run()