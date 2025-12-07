package year2023

import adventday.AdventDay
import adventday.InputRepresentation
import graph.dijkstra
import year2023.day10.PipeMap
import year2023.day10.PipeMapWithSqueezingBetweenPipes

object Day10 : AdventDay(2023, 10, "Pipe Maze") {
    override fun part1(input: InputRepresentation): Long {
        val map = PipeMap.of(input.lines)
        val (distance) = map.getAsGraph().dijkstra(map.start) { _, _ -> 1 }
        return map.keys.maxOf { distance(it) ?: Long.MIN_VALUE }
    }

    override fun part2(input: InputRepresentation): Any {
        val map = PipeMap.of(input.lines)
        val expandedMap = PipeMapWithSqueezingBetweenPipes(map)
        return expandedMap.countNumberOfTilesWithinLoop()
    }
}

fun main() = Day10.run()
