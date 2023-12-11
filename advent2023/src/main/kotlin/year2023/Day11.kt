package year2023

import AdventDay
import Point2D
import findAllPositionsOf
import kotlin.math.abs

object Day11 : AdventDay(2023, 11) {
    private fun Collection<Int>.computeOffsetFor(index: Int): Int =
        this.count { it < index } + index

    private fun Point2D.manhattenDistance(other: Point2D): Long = abs(x - other.x) + abs(y - other.y)
    override fun part1(input: List<String>): Any {
        val blankColumns = input[0].indices.filter { input.all { line -> line[it] == '.' } }.toSet()
        val skyMapExpanded = input
            .flatMap { line ->
                if (line.all { c -> c == '.' })
                    listOf(line, line)
                else
                    listOf(line)
            }
            .flatMapIndexed { y, line ->
                line.flatMapIndexed { x, char ->
                    val pointItself = Point2D(blankColumns.computeOffsetFor(x), y) to char
                    if (x in blankColumns) {
                        listOf(pointItself, pointItself.first + Point2D.RIGHT to '.')
                    } else {
                        listOf(pointItself)
                    }
                }
            }.associate { it }
        val galaxies = skyMapExpanded.filterValues { it == '#' }.keys.toList()
        val pairs = buildList {
            galaxies.indices.forEach { x ->
                (x..galaxies.indices.last).forEach { y ->
                    if (x != y) {
                        this += galaxies[x] to galaxies[y]
                    }
                }
            }
        }
        return pairs.sumOf { (a, b) -> a.manhattenDistance(b) }
    }

    override fun part2(input: List<String>): Any {
        val blankRows = input.indices.filter { input[it].all { c -> c == '.' } }.toSet()
        val blankColumns = input[0].indices.filter { input.all { line -> line[it] == '.' } }.toSet()
        val unadjustedGalaxies = input.findAllPositionsOf()
        val galaxies = unadjustedGalaxies.map {
            val newX = it.x + blankColumns.count { c -> c < it.x }.toLong() * (1_000_000 - 1).toLong()
            val newY = it.y + blankRows.count { c -> c < it.y }.toLong() * (1_000_000 - 1).toLong()
            Point2D(newX, newY)
        }
        val pairs = buildList {
            galaxies.indices.forEach { x ->
                (x..galaxies.indices.last).forEach { y ->
                    if (x != y) {
                        this += galaxies[x] to galaxies[y]
                    }
                }
            }
        }
        return pairs.sumOf { (a, b) -> a.manhattenDistance(b) }
    }
}

fun main() = Day11.run()
