package year2015

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import findAllPositionsOf

class Day18(
    val gridSize: Long,
    val evaluateAfterXSteps: Int
) : AdventDay(2015, 18, "Like a GIF For Your Yard") {
    val fourCorners by lazy {
        setOf(
            Point2D.ORIGIN,
            Point2D(0, gridSize - 1),
            Point2D(gridSize - 1, 0),
            Point2D(gridSize - 1, gridSize - 1),
        )
    }

    private fun InputRepresentation.simulateAndCountLightsAfterEnd(
        alwaysOnPoints: Set<Point2D> = emptySet()
    ): Int = lines
        .findAllPositionsOf()
        .let {
            generateSequence(it + alwaysOnPoints) { currentOn ->
                buildSet {
                    addAll(alwaysOnPoints)
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
            // disabled visual debug
            //                .prettyPrint()
        }
        .drop(1) // ignore start configuration
        .take(evaluateAfterXSteps)
        .last()
        .size

    // region visual debug helpers
    fun Set<Point2D>.prettyPrint() = (0..<gridSize).forEach { y ->
        println((0..<gridSize).joinToString("") { x ->
            if (Point2D(x, y) in this) "#" else "."
        })
    }

    @Suppress("unused")
    fun Sequence<Set<Point2D>>.prettyPrint() = withIndex()
        .map {
            if (it.index == 0)
                println("Initial state:")
            else {
                val pluralization = if (it.index > 1) "s" else ""
                println("After ${it.index} step$pluralization:")
            }
            it.value.prettyPrint()
            if (it.index < evaluateAfterXSteps) println()
            it.value
        }
    // endregion

    override fun part1(input: InputRepresentation): Int = input.simulateAndCountLightsAfterEnd()

    override fun part2(input: InputRepresentation): Int = input
        .simulateAndCountLightsAfterEnd(fourCorners)
}

fun main() = Day18(100, 100).run()
