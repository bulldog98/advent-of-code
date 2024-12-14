package year2024

import AdventDay
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
        fun move(steps: Long, xSize: Long, ySize: Long): RobotState {
            val xCycleLength = xSize / gcd(velocity.x.absoluteValue, xSize)
            val yCycleLength = ySize / gcd(velocity.y.absoluteValue, ySize)
            val cycleLength = lcm(xCycleLength, yCycleLength)
            val stepsToDo = steps % cycleLength
            // println("detected cycle length $cycleLength, only do $stepsToDo")
            return copy(
                position = Point2D(
                    (stepsToDo * xSize + position.x + stepsToDo * velocity.x) % xSize,
                    (stepsToDo * ySize + position.y + stepsToDo * velocity.y) % ySize,
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
        val after100 = initialRobots.map { it.move(100, width, height) }
        val q1 = after100.groupBy { it.position.x in 0..<width / 2 && it.position.y in 0..<height / 2 }
            .getOrDefault(true, emptyList()).size
        val q2 = after100.groupBy { it.position.x in width / 2 + 1..<width && it.position.y in 0..<height / 2 }
            .getOrDefault(true, emptyList()).size
        val q3 = after100.groupBy { it.position.x in 0..<width / 2 && it.position.y in height / 2 + 1..<height }
            .getOrDefault(true, emptyList()).size
        val q4 = after100.groupBy { it.position.x in width / 2 + 1..<width && it.position.y in height / 2 + 1..<height }
            .getOrDefault(true, emptyList()).size
        return q1.toLong() * q2 * q3 * q4
    }

    override fun part2(input: List<String>): Int {
        val initialRobots = input.map { RobotState.parse(it) }
        val result = generateSequence(initialRobots) { robots -> robots.map { robot -> robot.move(1, width, height) }}
            .take(height.toInt() * width.toInt())
            .withIndex()
            .first { it.value.isDisplayingChristmasTree() }
        println(generateField(result.value) { if (it == 0) '.' else '*' })
        return result.index
    }
}

fun main() = Day14(101, 103).run()