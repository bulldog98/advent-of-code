package year2021

import AdventDay
import year2021.day09.HeightMap
import year2021.day09.computeBasin

object Day09 : AdventDay(2021, 9) {
    override fun part1(input: List<String>): Int {
        val heightMap = HeightMap(input)
        return heightMap.lowPoints.sumOf { heightMap[it] + 1 }
    }

    override fun part2(input: List<String>): Int {
        val heightMap = HeightMap(input)
        return heightMap.lowPoints
            .map { it.computeBasin(heightMap).size }
            .sortedDescending()
            .take(3)
            .fold(1, Int::times)
    }
}

fun main() = Day09.run()
