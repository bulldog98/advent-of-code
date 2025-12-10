package year2021

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import helper.numbers.toAllLongs
import kotlin.math.absoluteValue
import kotlin.math.sign

object Day17 : AdventDay(2021, 17, "Trick Shot") {
    // x's positive, y's negative
    private data class TargetArea(val xRange: LongRange, val yRange: LongRange) {
        private data class ProbePosition(val position: Point2D, val velocity: Point2D) {
            private fun nextStep(): ProbePosition = copy(
                position = position + velocity,
                velocity = Point2D(
                    x = velocity.x - velocity.x.sign,
                    y = velocity.y - 1
                )
            )

            fun simulate(targetArea: TargetArea) = generateSequence(this) { it.nextStep() }
                .takeWhile { it.position.x <= targetArea.xRange.last && it.position.y >= targetArea.yRange.first }
        }

        fun computeAllSolutions() = (0..xRange.last).asSequence().flatMap { x ->
            (yRange.first..yRange.first.absoluteValue).map { y ->
                Point2D(x, y)
            }
        }.map { ProbePosition(Point2D.ORIGIN, it).simulate(this) }
            .filter { it.any { p -> p.position.x in xRange && p.position.y in yRange } }
            .map { it.map { p -> p.position } }

        companion object {
            fun parse(input: String) = input.toAllLongs().toList().let { (x1, x2, y1, y2) ->
                TargetArea(x1..x2, y1..y2)
            }
        }
    }

    override fun part1(input: InputRepresentation): Long = TargetArea.parse(input.text)
        .computeAllSolutions().maxOf {
            it.maxOf { p -> p.y }
        }

    override fun part2(input: InputRepresentation): Long = TargetArea.parse(input.text)
        .computeAllSolutions().count().toLong()
}

fun main() = Day17.run()
