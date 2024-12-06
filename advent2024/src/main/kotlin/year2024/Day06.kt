package year2024

import AdventDay
import Point2D
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
    override fun part1(input: List<String>): Int {
        val barriers = input.findAllPositionsOf('#')
        val startPosition = input.findAllPositionsOf('^').first()
        val maxX = input[0].length - 1
        val maxY = input.size - 1

        return (startPosition to Point2D.UP).explore(maxX.toLong(), maxY.toLong(), barriers)
            .map { (pos, _) -> pos }
            .toSet().size
    }

    override fun part2(input: List<String>): Int {
        val barriers = input.findAllPositionsOf('#')
        val startPosition = input.findAllPositionsOf('^').first()
        val maxX = input[0].length - 1
        val maxY = input.size - 1

        return (0..maxX).sumOf { x ->
            (0..maxY).count { y ->
                if (Point2D(x, y) in barriers)
                    return@count false
                val visited = mutableSetOf(startPosition to Point2D.UP)
                (startPosition to Point2D.UP).explore(maxX.toLong(), maxY.toLong(), barriers + Point2D(x, y))
                    .take(maxX * maxY).drop(1).forEach {
                        if (it in visited) {
                            return@count true
                        }
                        visited += it
                    }
                return@count false
            }
        }
    }
}

fun main() = Day06.run()