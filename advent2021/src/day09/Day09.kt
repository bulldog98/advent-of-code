package day09

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val heightMap = HeightMap(input)
        return heightMap.lowPoints.sumOf { heightMap[it] + 1 }
    }

    fun part2(input: List<String>): Int {
        val heightMap = HeightMap(input)
        return heightMap.lowPoints
            .map { it.computeBasin(heightMap).size }
            .sortedDescending()
            .take(3)
            .fold(1, Int::times)
    }

    val testInput = readInput("day09/Day09_test")
    val input = readInput("day09/Day09")

    // test if implementation meets criteria from the description:
    check(part1(testInput) == 15)
    println(part1(input))

    // test if implementation meets criteria from the description:
    check(part2(testInput) == 1134)
    println(part2(input))
}