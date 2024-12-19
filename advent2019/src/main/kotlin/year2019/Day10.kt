package year2019

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import findAllPositionsOf
import gcd
import kotlin.math.absoluteValue

object Day10: AdventDay(2019, 10) {
    private fun Set<Point2D>.canBeSeenBy(viewPosition: Point2D): List<Point2D> = filter { other ->
        val xDiff = other.x - viewPosition.x
        val yDiff = other.y - viewPosition.y
        val gcd = if (xDiff == 0L || yDiff == 0L)
            maxOf(xDiff.absoluteValue, yDiff.absoluteValue)
        else
            gcd(xDiff.absoluteValue, yDiff.absoluteValue)
        val xSteps = xDiff / gcd
        val ySteps = yDiff / gcd
        val vector = Point2D(xSteps, ySteps)

        (1..<gcd).all { viewPosition + vector * it !in this }
    }

    override fun part1(input: InputRepresentation): Long {
        val asteroids = input.findAllPositionsOf('#')
        return asteroids.maxOf { (asteroids - it).canBeSeenBy(it).size.toLong() }
    }

    override fun part2(input: InputRepresentation): Any {
        TODO("Not yet implemented")
    }
}



fun main() = Day10.run()