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

object Day06 : AdventDay(2024, 6) {
    override fun part1(input: List<String>): Int {
        val barriers = input.findAllPositionsOf('#')
        val startPosition = input.findAllPositionsOf('^').first()
        val maxX = input[0].length - 1
        val maxY = input.size - 1

        return generateSequence(startPosition to Point2D.UP) { (pos, dir) ->
            if (pos + dir in barriers) {
                pos to dir.rotateRight()
            } else {
                pos + dir to dir
            }
        }.takeWhile { (pos, _) -> (pos.x in 0..maxX) && pos.y in 0..maxY }
            .map { (pos, _) -> pos }
            .toSet().size
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day06.run()