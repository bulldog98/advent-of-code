package year2024

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import collections.pairings
import findAllPositionsOf

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
        return listOf(
            antennaA + 2 * (antennaB - antennaA),
            antennaB + 2 * (antennaA - antennaB),
        ).filter { it.x in xRange && it.y in yRange }
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

object Day08 : AdventDay(2024, 8, "Resonant Collinearity") {
    override fun part1(input: InputRepresentation) = WithoutHarmonies.countAntiNodes(input.lines)

    override fun part2(input: InputRepresentation) = WithHarmonies.countAntiNodes(input.lines)
}

fun main() = Day08.run()