package year2021

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import helper.numbers.toAllLongs
import kotlin.math.absoluteValue
import kotlin.math.sign

object Day17 : AdventDay(2021, 17) {
    // x's positive, y's negative
    data class TargetArea(val xRange: LongRange, val yRange: LongRange) {
        companion object {
            fun parse(input: String) = input.toAllLongs().toList().let { (x1, x2, y1, y2) ->
                TargetArea(x1..x2, y1..y2)
            }
        }
    }

    data class ProbePosition(val position: Point2D, val velocity: Point2D) {
        fun nextStep(): ProbePosition = copy(
            position = position + velocity,
            velocity = Point2D(
                x = velocity.x - velocity.x.sign,
                y = velocity.y - 1
            )
        )

        fun simulate(targetArea: TargetArea) = generateSequence(this) { it.nextStep() }
            .takeWhile { it.position.x <= targetArea.xRange.last && it.position.y >= targetArea.yRange.first }
    }

    override fun part1(input: InputRepresentation): Long {
        val targetArea = TargetArea.parse(input.asText())
        val velocities = (0..targetArea.xRange.last).asSequence().flatMap { x ->
            (targetArea.yRange.first..targetArea.yRange.first.absoluteValue).map { y ->
                Point2D(x, y)
            }
        }
        return velocities.map { ProbePosition(Point2D.ORIGIN, it).simulate(targetArea) }.maxOf {
            val path = it.toList()
            if (path.any { p -> p.position.x in targetArea.xRange && p.position.y in targetArea.yRange })
                path.maxOf { p -> p.position.y }
            else
                Long.MIN_VALUE
        }
    }

    override fun part2(input: InputRepresentation): Long {
        TODO("Not yet implemented")
    }
}

fun main() = Day17.run()
