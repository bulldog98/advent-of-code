package year2023

import AdventDay
import Point2D

object Day18: AdventDay(2023, 18) {
    operator fun Point2D.times(times: Int): Point2D =
        Point2D(x * times, y * times)
    private fun parseInstruction(input: String): Pair<Point2D, Int> = when (input.first()) {
        'R' -> Point2D.RIGHT
        'L' -> Point2D.LEFT
        'U' -> Point2D.UP
        'D' -> Point2D.DOWN
        else -> error("unknown direction")
    } to input.split(" ")[1].toInt()

    override fun part1(input: List<String>): Int {
        val instructions = input.map(::parseInstruction)
        val currentPosition = Point2D(0, 0)
        val walls = instructions.fold(setOf(currentPosition) to currentPosition) { (visited, curr), (dir, times) ->
            val newVisits = (0 until times).runningFold(curr) { cur, _ ->
                cur + dir
            }
            visited + newVisits to newVisits.last()
        }.first
        val alreadyHollow = walls.size
        val topLeftOutside = Point2D(walls.minOf { it.x } - 1, walls.minOf { it.y } - 1)
        val bottomRightOutside = Point2D(walls.maxOf { it.x } + 1, walls.maxOf { it.y } + 1)
        val xRange = (topLeftOutside.x..bottomRightOutside.x)
        val yRange = (topLeftOutside.y..bottomRightOutside.y)
        val visited = walls.toMutableSet()
        val queue = mutableListOf(topLeftOutside)
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            visited += current
            queue += current.cardinalNeighbors.filter {
                it.x in xRange && it.y in yRange && it !in visited && it !in queue
            }
        }
        return xRange.sumOf { x ->
            yRange.count { y -> Point2D(x, y) !in visited }
        } + alreadyHollow
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day18.run()
