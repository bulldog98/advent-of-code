package year2024

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import gcd
import helper.numbers.toAllLongs
import lcm
import kotlin.math.absoluteValue

class Day14(
    private val width: Long,
    private val height: Long
) : AdventDay(2024, 14, "Restroom Redoubt") {
    private data class RobotState(val position: Point2D, val velocity: Point2D) {
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

    private data class RobotField(
        private val robots: List<RobotState>,
        private val width: Long,
        private val height: Long,
    ) {
        val theoreticalMaxRounds by lazy {
            robots.maxOf { lcm(listOf(height, width, it.velocity.x.absoluteValue, it.velocity.y.absoluteValue)) }
                .toInt()
        }

        fun isDisplayingChristmasTree() = robots.any { robot ->
            val pos = robot.position
            val offsets = listOf(Point2D.DOWN) +
                listOf(Point2D.LEFT, Point2D.ORIGIN, Point2D.RIGHT).map { it + Point2D.DOWN } +
                listOf(
                    Point2D.LEFT + Point2D.LEFT,
                    Point2D.LEFT,
                    Point2D.ORIGIN,
                    Point2D.RIGHT,
                    Point2D.RIGHT + Point2D.RIGHT
                ).map { it + Point2D.DOWN + Point2D.DOWN }
            offsets.map { it + pos }.all { searchPosition -> robots.any { it.position == searchPosition } }
        }

        fun move(steps: Long = 1L) = copy(
            robots = robots.map { robot -> robot.move(width, height, steps) }
        )

        fun count(isCounted: (RobotState) -> Boolean) = robots.count(isCounted)

        /**
         * helper function to print the robots in the room
         */
        fun generateField(display: (Int) -> Char = { if (it == 0) '0' else it.digitToChar() }) =
            (0..<height).joinToString("\n") { y ->
                (0..<width).map { x ->
                    val res = robots.count { it.position.x == x && it.position.y == y }
                    display(res)
                }.joinToString("")
            }

        companion object {
            fun parse(input: InputRepresentation, width: Long, height: Long) = RobotField(
                robots = input.lines.map { it: String -> RobotState.parse(it) },
                width = width,
                height = height
            )
        }
    }

    override fun part1(input: InputRepresentation): Long {
        val initialRobots = RobotField.parse(input, width, height)
        val after100 = initialRobots.move(100)
        val quadrants = listOf(
            Point2D.ORIGIN..<Point2D(width / 2, height / 2),
            Point2D(width / 2 + 1, 0)..<Point2D(width, height / 2),
            Point2D(0, height / 2 + 1)..<Point2D(width / 2, height),
            Point2D(width / 2 + 1, height / 2 + 1)..<Point2D(width, height)
        )
        return quadrants.fold(1L) { acc, quadrant ->
            acc * after100.count { it.position in quadrant }
        }
    }

    override fun part2(input: InputRepresentation): Int {
        val initialRobots = RobotField.parse(input, width, height)
        val theoreticalMaxRounds = initialRobots.theoreticalMaxRounds
        val (index, result) = generateSequence(initialRobots) { robots ->
            robots.move()
        }
            .take(theoreticalMaxRounds)
            .withIndex()
            .first { (_, robots) -> robots.isDisplayingChristmasTree() }
        println(result.generateField { if (it == 0) '.' else '*' })
        return index
    }
}

fun main() = Day14(101, 103).run()