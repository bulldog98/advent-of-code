package year2019

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import kotlinx.coroutines.runBlocking
import year2019.computer.IntComputer

object Day11: AdventDay(2019, 11) {
    fun Point2D.rotateLeft90Degrees() = when (this) {
        Point2D.UP -> Point2D.LEFT
        Point2D.LEFT -> Point2D.DOWN
        Point2D.DOWN -> Point2D.RIGHT
        Point2D.RIGHT -> Point2D.UP
        else -> error("no valid direction")
    }
    fun Point2D.rotateRight90Degrees() = when (this) {
        Point2D.UP -> Point2D.RIGHT
        Point2D.RIGHT -> Point2D.DOWN
        Point2D.DOWN -> Point2D.LEFT
        Point2D.LEFT -> Point2D.UP
        else -> error("no valid direction")
    }

    override fun part1(input: InputRepresentation): Long = runBlocking {
        val visitedWithColor = mutableMapOf(Point2D.ORIGIN to 0L)
        var currentPosition = Point2D.ORIGIN
        var colorOutput = true
        var direction = Point2D.UP
        val computer = IntComputer.parseWithSuspendInputOutput(input, { visitedWithColor.getOrPut(currentPosition) { 0L } }) {
            if (colorOutput) {
                visitedWithColor[currentPosition] = it
            } else {
                direction = when (it) {
                    0L -> direction.rotateLeft90Degrees()
                    1L -> direction.rotateRight90Degrees()
                    else -> error("should not happen")
                }
                currentPosition += direction
            }
            colorOutput = !colorOutput
        }
        computer.simulateUntilHaltWithInterruptions()
        visitedWithColor.size.toLong()
    }

    override fun part2(input: InputRepresentation): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day11.run()