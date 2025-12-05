package year2023

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import findAllPositionsOf

object Day11 : AdventDay(2023, 11) {
    private fun manhattanDistance(point2DPoint2DPair: Pair<Point2D, Point2D>) =
        point2DPoint2DPair.first.manhattanDistance(point2DPoint2DPair.second)

    private fun List<Point2D>.allPairs() =
        buildList {
            this@allPairs.indices.forEach { x ->
                (x..this@allPairs.indices.last).forEach { y ->
                    if (x != y) {
                        this += this@allPairs[x] to this@allPairs[y]
                    }
                }
            }
        }

    private fun List<String>.blankColumIndices() =
        this[0].indices.filter { all { line -> line[it] == '.' } }.toSet()
    private fun List<String>.blankRowIndices() =
        indices.filter { this[it].all { c -> c == '.' } }.toSet()

    private fun List<String>.computeGalaxiesWithWithAdjustment(expansionForEmpty: Long = 2): List<Point2D> {
        val blankRows = blankRowIndices()
        val blankColumns = blankColumIndices()
        val unadjustedGalaxies = findAllPositionsOf()
        return unadjustedGalaxies.map {
            val newX = it.x + blankColumns.count { c -> c < it.x }.toLong() * (expansionForEmpty - 1L)
            val newY = it.y + blankRows.count { c -> c < it.y }.toLong() * (expansionForEmpty - 1L)
            Point2D(newX, newY)
        }
    }

    override fun part1(input: InputRepresentation): Any {
        val galaxies = input.lines.computeGalaxiesWithWithAdjustment(2)
        val pairs = galaxies.allPairs()
        return pairs.sumOf(::manhattanDistance)
    }

    override fun part2(input: InputRepresentation): Any {
        val galaxies = input.lines.computeGalaxiesWithWithAdjustment(1_000_000L)
        val pairs = galaxies.allPairs()
        return pairs.sumOf(::manhattanDistance)
    }
}

fun main() = Day11.run()
