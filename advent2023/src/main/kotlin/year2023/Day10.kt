package year2023

import AdventDay
import graph.dijkstra
import year2023.day10.PipeMap
import year2023.day10.PipeMapWithSqueezingBetweenPipes

object Day10 : AdventDay(2023, 10) {
    override fun part1(input: List<String>): Long {
        val map = PipeMap.of(input)
        val (distance) = map.getAsGraph().dijkstra(map.start) { _, _ -> 1 }
        return map.keys.maxOf { distance(it) ?: Long.MIN_VALUE }
    }

    override fun part2(input: List<String>): Any {
        val map = PipeMap.of(input)
        val expandedMap = PipeMapWithSqueezingBetweenPipes(map)
        return expandedMap.countNumberOfTilesWithinLoop()
    }
}

fun main() = Day10.run()
