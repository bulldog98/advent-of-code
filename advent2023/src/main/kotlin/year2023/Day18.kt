package year2023

import adventday.AdventDay
import Point2D
import kotlin.math.abs

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

    private fun parseInstructionPart2(input: String): Pair<Point2D, Int> = input.split("(#", ")")[1].let { hex ->
        val count = hex.dropLast(1).toInt(16)
        val direction = when (hex.last()) {
            '0' -> Point2D.RIGHT
            '1' -> Point2D.DOWN
            '2' -> Point2D.LEFT
            '3' -> Point2D.UP
            else -> error("invalid direction")
        }
        direction to count
    }

    private fun List<Point2D>.computePolygonSize(n: Int) =
        abs((1 .. n).sumOf { i ->
            this[i % n].x * (this[(i + 1) % n].y - this[(i - 1) % n].y)
        }) / 2L

    override fun part1(input: List<String>): Long {
        val instructions = input.map(::parseInstructionPart1)
        val edges = instructions.fold(listOf(Point2D(0, 0))) { cur, (dir, times) ->
            cur + (cur.last() + dir * times)
        }
        return edges.computePolygonSize(edges.size - 1) +
            edges.zipWithNext().sumOf { (a, b) -> abs(a.x - b.x) + abs(a.y - b.y) }/2 + 1
    }

    override fun part2(input: List<String>): Long {
        val instructions = input.map(::parseInstructionPart2)
        val edges = instructions.fold(listOf(Point2D(0, 0))) { cur, (dir, times) ->
            cur + (cur.last() + dir * times)
        }
        return edges.computePolygonSize(edges.size - 1) +
            edges.zipWithNext().sumOf { (a, b) -> abs(a.x - b.x) + abs(a.y - b.y) }/2 + 1
    }
}

fun main() = Day18.run()
