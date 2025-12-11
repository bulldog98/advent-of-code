package year2015

import NotYetImplemented
import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import findAllPositionsOf

class Day18(
    val gridSize: Long,
    val evaluateAfterXSteps: Int
) : AdventDay(2015, 18, "Like a GIF For Your Yard") {
    override fun part1(input: InputRepresentation): Int = input
        .lines
        .findAllPositionsOf()
        .let {
            generateSequence(it) { currentOn ->
                buildSet {
                    currentOn.forEach { on ->
                        when (on.neighborHood.count { it in currentOn }) {
                            2, 3 -> add(on)
                            else -> {}
                        }
                    }
                    for (x in 0..<gridSize) {
                        for (y in 0..<gridSize) {
                            val point = Point2D(x, y)
                            if (point in currentOn) continue
                            if (point.neighborHood.count { it in currentOn } == 3) add(point)
                        }
                    }
                }
            }
        }
        .drop(1) // ignore start configuration
        .take(evaluateAfterXSteps)
        .last()
        .size

    override fun part2(input: InputRepresentation): Any =
        NotYetImplemented
}

fun main() = Day18(100, 100).run()
