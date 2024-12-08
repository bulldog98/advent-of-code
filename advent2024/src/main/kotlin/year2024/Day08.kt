package year2024

import AdventDay
import Point2D
import findAllPositionsOf
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun computeAntinodes(antennaA: Point2D, antennaB: Point2D): List<Point2D> {
    if (antennaA.x > antennaB.x) return computeAntinodes(antennaB, antennaA)
    // antennaA is left
    val minY = min(antennaA.y, antennaB.y)
    val maxY = max(antennaA.y, antennaB.y)
    val xDistance = abs(antennaB.x - antennaA.x)
    val yDistance = abs(antennaB.y - antennaA.y)
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

fun computeAntinodesWithHarmonies(antennaA: Point2D, antennaB: Point2D, xRange: IntRange): List<Point2D> =
    if (antennaA.x > antennaB.x)
        computeAntinodesWithHarmonies(antennaB, antennaA, xRange)
    else buildList {
        val mTop = antennaB.y - antennaA.y
        val cTop = antennaB.x * antennaA.y - antennaA.x * antennaB.y
        val mBottom = antennaB.x - antennaA.x
        addAll(
            xRange
                .filter { x ->
                    (x * mTop + cTop) % mBottom == 0L
                }
                .map { x -> Point2D(x, ((x*mTop + cTop) / mBottom).toInt()) }
        )
    }

object Day08 : AdventDay(2024, 8) {
    override fun part1(input: List<String>): Int {
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

    override fun part2(input: List<String>): Int {
        val frequencies = input.flatMap { it.toSet() }.toSet() - '.'
        val xRange = input[0].indices
        val yRange = input.indices
        val antinodes = buildSet {
            frequencies.forEach { c ->
                val antennas = input.findAllPositionsOf(c)
                if (antennas.size > 1) {
                    pairings(antennas.toList()).forEach { (a, b) ->
                        addAll(computeAntinodesWithHarmonies(a, b, xRange).filter { (_, y) -> y in yRange })
                    }
                }
            }
        }
        return antinodes.size
    }
}

fun main() = Day08.run()