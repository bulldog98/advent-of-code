package year2023

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import java.util.*

object Day17 : AdventDay(2023, 17, "Clumsy Crucible") {

    data class Field(val heatMap: Map<Point2D, Int>) {
        val bottomRight = heatMap.keys.maxBy { it.manhattanDistance(Point2D.ORIGIN) }
        companion object {
            private fun List<String>.computeHeatMap() = buildMap {
                forEachIndexed { y, row ->
                    row.forEachIndexed { x, c ->
                        this[Point2D(x, y)] = c.digitToInt()
                    }
                }
                this[Point2D.ORIGIN] = 0
            }
            fun of(input: List<String>) = Field(input.computeHeatMap())
        }
    }

    data class CurrentCrucibleState(
        val point: Point2D = Point2D.ORIGIN,
        val direction: Point2D,
        val moveMemory: Int = 1,
        val totalHeatLoss: Int = 0
    )

    private fun Point2D.clockwise(): Point2D = when (this) {
        Point2D.UP -> Point2D.RIGHT
        Point2D.RIGHT -> Point2D.DOWN
        Point2D.DOWN -> Point2D.LEFT
        Point2D.LEFT -> Point2D.UP
        else -> error("no direction")
    }

    private fun Point2D.counterClockwise(): Point2D = when (this) {
        Point2D.UP -> Point2D.LEFT
        Point2D.RIGHT -> Point2D.UP
        Point2D.DOWN -> Point2D.RIGHT
        Point2D.LEFT -> Point2D.DOWN
        else -> error("no direction")
    }

    private fun Field.solve(minimumStepsForward: Int, maximumStepsForward: Int): Int {
        // point, direction, steps done
        val visited = mutableSetOf(
            Triple(Point2D.ORIGIN, Point2D.RIGHT, 1),
            Triple(Point2D.ORIGIN, Point2D.DOWN, 1)
        )
        val queue = PriorityQueue(compareBy(CurrentCrucibleState::totalHeatLoss)).apply {
            add(CurrentCrucibleState(Point2D.ORIGIN, Point2D.RIGHT, 1, 0))
            add(CurrentCrucibleState(Point2D.ORIGIN, Point2D.DOWN, 1, 0))
        }
        fun add(currentCrucibleState: CurrentCrucibleState) {
            if (visited.add(Triple(currentCrucibleState.point, currentCrucibleState.direction, currentCrucibleState.moveMemory))) {
                queue.offer(currentCrucibleState)
            }
        }

        while (queue.isNotEmpty()) {
            val (point, direction, stepsInOneDirectionDone, totalHeatLoss) = queue.poll()
            val nextPoint = point + direction
            val nextScore = totalHeatLoss + (heatMap[nextPoint] ?: continue)
            if (nextPoint == bottomRight) {
                return nextScore
            }

            if (stepsInOneDirectionDone < maximumStepsForward) {
                add(CurrentCrucibleState(nextPoint, direction, stepsInOneDirectionDone + 1, nextScore))
            }

            if (stepsInOneDirectionDone >= minimumStepsForward) {
                listOf(direction.clockwise(), direction.counterClockwise()).forEach { nextDirection ->
                    add(CurrentCrucibleState(nextPoint, nextDirection, 1, nextScore))
                }
            }
        }
        error("could not determine score at end")
    }

    override fun part1(input: InputRepresentation) =
        Field.of(input.lines).solve(0, 3)

    override fun part2(input: InputRepresentation) =
        Field.of(input.lines).solve(4, 10)
}

fun main() = Day17.run()
