package year2024

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import findAllPositionsOf

fun Point2D.rotateRight(): Point2D = when (this) {
    Point2D.UP -> Point2D.RIGHT
    Point2D.RIGHT -> Point2D.DOWN
    Point2D.DOWN -> Point2D.LEFT
    Point2D.LEFT -> Point2D.UP
    else -> error("should not happen")
}

fun Pair<Point2D, Point2D>.explore(maxX: Long, maxY: Long, barriers: Collection<Point2D>): Sequence<Pair<Point2D, Point2D>> = generateSequence(this) { (pos, dir) ->
    if (pos + dir in barriers) {
        pos to dir.rotateRight()
    } else {
        pos + dir to dir
    }
}.takeWhile { (pos, _) -> (pos.x in 0..maxX) && pos.y in 0..maxY }

object Day06 : AdventDay(2024, 6) {
    override fun part1(input: InputRepresentation): Int {
        val barriers = input.findAllPositionsOf('#')
        val startPosition = input.findAllPositionsOf('^').first()
        val maxX = input[0].length - 1
        val maxY = input.size - 1

        return (startPosition to Point2D.UP).explore(maxX.toLong(), maxY.toLong(), barriers)
            .map { (pos, _) -> pos }
            .toSet().size
    }

    override fun part2(input: InputRepresentation): Int {
        val barriers = input.findAllPositionsOf('#')
        val startPosition = input.findAllPositionsOf('^').first()
        val maxX = input[0].length.toLong() - 1
        val maxY = input.size.toLong() - 1

        // only points that would be visited anyway are can result in a hit barrier
        return (startPosition to Point2D.UP).explore(maxX, maxY, barriers)
            .map { (pos, _) -> pos }
            .toSet()
            .count { additionalBarrier ->
                val visited = mutableSetOf(startPosition to Point2D.UP)
                (startPosition to Point2D.UP).explore(maxX, maxY, barriers + additionalBarrier)
                    .take((maxX * maxY).toInt()).drop(1).forEach {
                        if (it in visited) {
                            return@count true
                        }
                        visited += it
                    }
                return@count false
            }
    }
}

fun main() = Day06.run()