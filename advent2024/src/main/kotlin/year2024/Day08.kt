package year2024

import AdventDay
import Point2D
import collections.pairings
import findAllPositionsOf
import kotlin.math.abs

// use typealias so that the parameters have names
typealias Computation = (antennaA: Point2D, antennaB: Point2D, xRange: IntRange, yRange: IntRange) -> Collection<Point2D>

sealed interface AntiNodeComputation : Computation {
    fun countAntiNodes(input: List<String>): Int = buildSet {
        val frequencies = input.flatMap { it.toSet() }.toSet() - '.'
        val xRange = input[0].indices
        val yRange = input.indices
        frequencies.forEach { c ->
            val antennas = input.findAllPositionsOf(c)
            if (antennas.size > 1) {
                antennas.pairings().forEach { (a, b) ->
                    this.addAll(this@AntiNodeComputation(a, b, xRange, yRange))
                }
            }
        }
    }.size
}

data object WithoutHarmonies : AntiNodeComputation {
    override fun invoke(antennaA: Point2D, antennaB: Point2D, xRange: IntRange, yRange: IntRange): Collection<Point2D> {
        if (antennaA.x > antennaB.x) return invoke(antennaB, antennaA, xRange, yRange)
        val xDistance = abs(antennaB.x - antennaA.x)
        val yDistance = abs(antennaB.y - antennaA.y)
        return when {
            // line goes down
            antennaA.y > antennaB.y -> listOf(
                Point2D(antennaA.x - xDistance, antennaA.y + yDistance),
                Point2D(antennaB.x + xDistance, antennaB.y - yDistance)
            )
            // line goes up
            else -> listOf(
                Point2D(antennaA.x - xDistance, antennaA.y - yDistance),
                Point2D(antennaB.x + xDistance, antennaB.y + yDistance)
            )
        }.filter { (x, y) ->
            x in xRange && y in yRange
        }
    }
}

data object WithHarmonies : AntiNodeComputation {
    override fun invoke(antennaA: Point2D, antennaB: Point2D, xRange: IntRange, yRange: IntRange): Collection<Point2D> {
        if (antennaA.x > antennaB.x) return invoke(antennaB, antennaA, xRange, yRange)
        val mTop = antennaB.y - antennaA.y
        val cTop = antennaB.x * antennaA.y - antennaA.x * antennaB.y
        val mBottom = antennaB.x - antennaA.x
        return xRange.filter { x ->
            (x * mTop + cTop) % mBottom == 0L
        }.map { x ->
            Point2D(x, ((x * mTop + cTop) / mBottom).toInt())
        }.filter { (_, y) ->
            y in yRange
        }
    }
}

object Day08 : AdventDay(2024, 8) {
    override fun part1(input: List<String>) = WithoutHarmonies.countAntiNodes(input)

    override fun part2(input: List<String>) = WithHarmonies.countAntiNodes(input)
}

fun main() = Day08.run()