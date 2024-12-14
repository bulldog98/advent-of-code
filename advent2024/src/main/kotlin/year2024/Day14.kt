package year2024

import adventday.AdventDay
import Point2D
import gcd
import helper.numbers.toAllLongs
import lcm
import kotlin.math.absoluteValue

class Day14(
    private val width: Long,
    private val height: Long
) : AdventDay(2024, 14) {
    data class RobotState(val position: Point2D, val velocity: Point2D) {
        fun move(xBound: Long, yBound: Long, steps: Long = 1L): RobotState {
            val xCycleLength = xBound / gcd(velocity.x.absoluteValue, xBound)
            val yCycleLength = yBound / gcd(velocity.y.absoluteValue, yBound)
            val cycleLength = lcm(xCycleLength, yCycleLength)
            val stepsToDo = steps % cycleLength
            // println("detected cycle length $cycleLength, only do $stepsToDo")
            return copy(
                position = Point2D(
                    (stepsToDo * xBound + position.x + stepsToDo * velocity.x) % xBound,
                    (stepsToDo * yBound + position.y + stepsToDo * velocity.y) % yBound,
                )
            )
        }

        companion object {
            fun parse(input: String) = input.toAllLongs()
                .chunked(2) { (x, y) -> Point2D(x, y) }.toList().let { (a, b) -> RobotState(a, b) }
        }
    }

    private fun List<RobotState>.isDisplayingChristmasTree() = any { robot ->
        val pos = robot.position
        val offsets = listOf(Point2D.DOWN) +
            listOf(Point2D.LEFT, Point2D.ORIGIN, Point2D.RIGHT).map { it + Point2D.DOWN } +
            listOf(Point2D.LEFT + Point2D.LEFT, Point2D.LEFT, Point2D.ORIGIN, Point2D.RIGHT, Point2D.RIGHT + Point2D.RIGHT).map { it + Point2D.DOWN + Point2D.DOWN}
        offsets.map { it + pos }.all { searchPosition -> this.any { it.position == searchPosition } }
    }

    /**
     * helper function to print the robots in the room
     */
    private fun generateField(
        robots: List<RobotState>,
        display: (Int) -> Char = { if (it == 0) '0' else it.digitToChar() }
    ) =
        (0..<height).joinToString("\n") { y ->
            (0..<width).map { x ->
                val res = robots.count { it.position.x == x && it.position.y == y }
                display(res)
            }.joinToString("")
        }

    override fun part1(input: List<String>): Long {
        // 0, 0 is top left corner
        val initialRobots = input.map { RobotState.parse(it) }
        val after100 = initialRobots.map { it.move(width, height, 100) }
        val quadrants = listOf(
            Point2D.ORIGIN ..< Point2D(width / 2, height / 2),
            Point2D(width / 2 + 1, 0) ..< Point2D(width, height / 2),
            Point2D(0, height / 2 + 1) ..< Point2D(width / 2, height),
            Point2D(width / 2 + 1, height / 2 + 1) ..< Point2D(width, height)
        )
        return quadrants.fold(1L) { acc, quadrant ->
            acc * after100.count { it.position in quadrant }
        }
    }

    override fun part2(input: List<String>): Int {
        val initialRobots = input.map { RobotState.parse(it) }
        val theoreticalMaxRounds = initialRobots.maxOf { lcm(listOf(height, width, it.velocity.x.absoluteValue, it.velocity.y.absoluteValue)) }
            .toInt()
        val (index, result) = generateSequence(initialRobots) { robots -> robots.map { robot -> robot.move(
            width,
            height,
        ) }}
            .take(theoreticalMaxRounds)
            .withIndex()
            .first { (_, robots) -> robots.isDisplayingChristmasTree() }
        println(generateField(result) { if (it == 0) '.' else '*' })
        return index
    }
}

fun main() = Day14(101, 103).run()