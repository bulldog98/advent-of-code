package year2021

import adventday.AdventDay
import adventday.InputRepresentation
import year2021.day09.HeightMap
import year2021.day09.computeBasin

object Day09 : AdventDay(2021, 9, "Smoke Basin") {
    override fun part1(input: InputRepresentation): Int {
        val heightMap = HeightMap(input.lines)
        return heightMap.lowPoints.sumOf { heightMap[it] + 1 }
    }

    override fun part2(input: InputRepresentation): Int {
        val heightMap = HeightMap(input.lines)
        return heightMap.lowPoints
            .map { it.computeBasin(heightMap).size }
            .sortedDescending()
            .take(3)
            .fold(1, Int::times)
    }
}

fun main() = Day09.run()
