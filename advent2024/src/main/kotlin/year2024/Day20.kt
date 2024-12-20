package year2024

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import findAllPositionsOf
import graph.AdjacencyListGraph
import graph.Graph
import graph.dijkstra

class Day20(private val minImprovement: Int) : AdventDay(2024, 20) {
    private data class RaceTrack(
        val walls: Set<Point2D>,
        val start: Point2D,
        val finish: Point2D,
        val mapGraph: Graph<Point2D>
    ) {
        private val dijkstraResult by lazy {
            mapGraph.dijkstra(finish) { _, _ -> 1 }
        }
        val distanceToEnd: (Point2D) -> Long? by lazy {
            {
                if (it == finish) 0L else dijkstraResult.first(it)
            }
        }
        val way by lazy {
            generateSequence(start) { dijkstraResult.second(it) }.toList()
        }

        fun improvementOf(cheat: Cheat): Long = distanceToEnd(cheat.start)!! - distanceToEnd(cheat.end)!! - cheat.size
        companion object {
            fun parse(input: InputRepresentation): RaceTrack {
                val walls = input.findAllPositionsOf('#')
                val start = input.findAllPositionsOf('S').single()
                val finish = input.findAllPositionsOf('E').single()
                return RaceTrack(
                    walls,
                    start,
                    finish,
                    AdjacencyListGraph(input.findAllPositionsOf('.') + start + finish) { node ->
                        node.cardinalNeighbors.filter { it !in walls }
                    }
                )
            }
        }
    }

    private data class Cheat(val start: Point2D, val end: Point2D) {
        val size: Long
            get() = start.manhattanDistance(end)
    }

    private fun RaceTrack.computeCheatsOfExactLength(length: Int, minImprovement: Int): List<Cheat> {
        val offsets = (0..length.toLong()).flatMap { x ->
            (0..length.toLong()).filter { x + it == length.toLong() }.flatMap { y ->
                listOf(
                    Point2D.UP * y + Point2D.LEFT * x, Point2D.UP * y + Point2D.RIGHT * x,
                    Point2D.DOWN * y + Point2D.LEFT * x, Point2D.DOWN * y + Point2D.RIGHT * x
                ).distinct()
            }
        }
        return way.dropLast(length)
            .flatMap { potentialCheatStart ->
                potentialCheatStart.cardinalNeighbors.filter { it in walls }.flatMap {
                    offsets.map { it + potentialCheatStart }
                        .filter { it !in walls && it in mapGraph.nodes }
                        .map { Cheat(potentialCheatStart, it) }
                        .filter { improvementOf(it) >= minImprovement }
                }.distinct()
            }.distinct()
    }

    private fun RaceTrack.computeCheatsOfAtMostLength(length: Int, minImprovement: Int): List<Cheat> = (2..length).flatMap {
        computeCheatsOfExactLength(it, minImprovement)
    }

    override fun part1(input: InputRepresentation): Int {
        val raceTrack = RaceTrack.parse(input)
        val cheats = raceTrack.computeCheatsOfExactLength(2, minImprovement)
        return cheats.size
    }

    override fun part2(input: InputRepresentation): Any {
        val raceTrack = RaceTrack.parse(input)
        val cheats = raceTrack.computeCheatsOfAtMostLength(20, minImprovement)
        return cheats.size
    }
}

fun main() = Day20(100).run()