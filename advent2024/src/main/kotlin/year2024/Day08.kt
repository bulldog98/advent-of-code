package year2024

import AdventDay
import Point2D
import findAllPositionsOf
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun computeAntinodes(antennaA: Point2D, antennaB: Point2D): List<Point2D> {
    val minY = min(antennaA.y, antennaB.y)
    val maxY = max(antennaA.y, antennaB.y)
    val xDistance = abs(antennaB.x - antennaA.x)
    val yDistance = abs(antennaB.y - antennaA.y)
    if (antennaA.x > antennaB.x) return computeAntinodes(antennaB, antennaA)
    // antennaA is left
    return when (antennaA.y) {
        // line goes down
        maxY -> listOf(
            Point2D(antennaA.x - xDistance, antennaA.y + yDistance),
            Point2D(antennaB.x + xDistance, antennaB.y - yDistance)
        )
        // line goes up
        minY -> listOf(
            Point2D(antennaA.x - xDistance, antennaA.y - yDistance),
            Point2D(antennaB.x + xDistance, antennaB.y + yDistance)
        )
        else -> error("should not happen")
    }
}

fun pairings(list: List<Point2D>) = buildList {
    if (list.isEmpty() || list.size == 1)
        return@buildList
    list.forEachIndexed { index, a ->
        list.forEachIndexed { index2, b ->
            if (index2 > index) {
                add(a to b)
            }
        }
    }
}

fun Point2D.isInLine(b: Point2D): Boolean =
    (b.y - y) % (b.x - x) == 0L && (x * b.y - b.x * y) % (b.x - x) == 0L

object Day08 : AdventDay(2024, 8) {
    override fun part1(input: List<String>): Any {
        val frequencies = input.flatMap { it.toSet() }.toSet() - '.'
        val xRange = input[0].indices
        val yRange = input.indices
        val antinodes = buildSet {
            frequencies.forEach { c ->
                val antennas = input.findAllPositionsOf(c)
                if (antennas.size > 1) {
                    pairings(antennas.toList()).forEach { (a, b) ->
                        addAll(computeAntinodes(a, b).filter { (x, y) -> x in xRange && y in yRange })
                    }
                }
            }
        }
        return antinodes.size
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day08.run()