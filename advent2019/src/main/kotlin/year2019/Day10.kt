package year2019

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import findAllPositionsOf
import gcd
import kotlin.math.absoluteValue
import kotlin.math.atan2

object Day10: AdventDay(2019, 10) {
    // computes the smallest vector x and c such that this + c * x == other, for some c >= 0
    private fun Point2D.directionVector(other: Point2D): Pair<Point2D, Long> = when {
        this == other -> error("no such vector")
        else -> {
            val xDiff = other.x - x
            val yDiff = other.y - y
            val gcd = if (xDiff == 0L || yDiff == 0L)
                maxOf(xDiff.absoluteValue, yDiff.absoluteValue)
            else
                gcd(xDiff.absoluteValue, yDiff.absoluteValue)
            val xSteps = xDiff / gcd
            val ySteps = yDiff / gcd
            Point2D(xSteps, ySteps) to gcd
        }
    }

    private data class Quadrants(val middle: Point2D) {
        fun isInQ1(point: Point2D) = point.x >= middle.x && point.y < middle.y
        fun isInQ2(point: Point2D) = point.x > middle.x && point.y >= middle.y
        fun isInQ3(point: Point2D) = point.x <= middle.x && point.y > middle.y
        fun isInQ4(point: Point2D) = point.x < middle.x && point.y <= middle.y
    }

    private fun Set<Point2D>.canBeSeenBy(viewPosition: Point2D): List<Point2D> = filter { other ->
        val (vector, c) = viewPosition.directionVector(other)
        (1..<c).all { viewPosition + vector * it !in this }
    }

    override fun part1(input: InputRepresentation): Long {
        val asteroids = input.findAllPositionsOf('#')
        return asteroids.maxOf { (asteroids - it).canBeSeenBy(it).size.toLong() }
    }

    override fun part2(input: InputRepresentation): Long {
        // the X is for the laser station in the examples
        val asteroids = input.findAllPositionsOf('#') + input.findAllPositionsOf('X')
        val laserStation = asteroids.maxBy { (asteroids - it).canBeSeenBy(it).size.toLong() }

        val quadrant = Quadrants(laserStation)
        val remainingAsteroids = (asteroids - laserStation).toMutableSet()
        val quadrantsToShoot = generateSequence(1) { it }.flatMap { listOf(quadrant::isInQ1, quadrant::isInQ2, quadrant::isInQ3, quadrant::isInQ4) }
        var shot = 1
        quadrantsToShoot.forEach { isInQuadrantToShoot ->
            val toShot = remainingAsteroids.filter { isInQuadrantToShoot(it) }.toSet().canBeSeenBy(laserStation)
                .sortedBy { atan2((it.y - laserStation.y).toDouble(), (it.x - laserStation.x).toDouble()) }
            toShot.forEach {
                shot++
                remainingAsteroids -= it
                if (shot == 200) return it.x * 100 + it.y
            }
            if (remainingAsteroids.isEmpty()) error("no solution found")
        }
        error("no solution found")
    }
}

fun main() = Day10.run()