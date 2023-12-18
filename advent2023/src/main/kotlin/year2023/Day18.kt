package year2023

import AdventDay
import Point2D

object Day18 : AdventDay(2023, 18) {
    operator fun Point2D.times(times: Int): Point2D =
        Point2D(x * times, y * times)

    private fun parseInstructionPart1(input: String): Pair<Point2D, Int> = when (input.first()) {
        'R' -> Point2D.RIGHT
        'L' -> Point2D.LEFT
        'U' -> Point2D.UP
        'D' -> Point2D.DOWN
        else -> error("unknown direction")
    } to input.split(" ")[1].toInt()

    private fun List<Pair<Point2D, Int>>.computeWall() =
        fold(setOf(Point2D(0, 0)) to Point2D(0, 0)) { (visited, curr), (dir, times) ->
            val newVisits = (0 until times).runningFold(curr) { cur, _ ->
                cur + dir
            }
            visited + newVisits to newVisits.last()
        }.first

    private fun Set<Point2D>.countPointsNotFloodedFromOutside(): Int {
        val topLeftOutside = Point2D(minOf { it.x } - 1, minOf { it.y } - 1)
        val bottomRightOutside = Point2D(maxOf { it.x } + 1, maxOf { it.y } + 1)
        val xRange = (topLeftOutside.x..bottomRightOutside.x)
        val yRange = (topLeftOutside.y..bottomRightOutside.y)
        val visited = toMutableSet()
        val queue = mutableListOf(topLeftOutside)
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            visited += current
            queue += current.cardinalNeighbors.filter {
                it.x in xRange && it.y in yRange && it !in visited && it !in queue
            }
        }
        return xRange.count() * yRange.count() - visited.size
    }

    override fun part1(input: List<String>): Int {
        val instructions = input.map(::parseInstructionPart1)
        val walls = instructions.computeWall()
        val alreadyHollow = walls.size
        return walls.countPointsNotFloodedFromOutside() + alreadyHollow
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day18.run()
