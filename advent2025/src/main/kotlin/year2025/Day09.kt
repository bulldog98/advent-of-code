package year2025

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import collections.pairings

object Day09 : AdventDay(2025, 9, "Movie Theater") {
    private fun Collection<Point2D>.areaOfLargestRectangle(
        // assume first parameter upperLeft corner, second downRight corner
        validRectangle: (Point2D, Point2D) -> Boolean = { _, _ -> true }
    ) = pairings()
        .maxOf { (a, b) ->
            val leftMost = listOf(a, b).minOf { it.x }
            val rightMost = listOf(a, b).maxOf { it.x }
            val upMost = listOf(a, b).minOf { it.y }
            val downMost = listOf(a, b).maxOf { it.y }
            if (validRectangle(Point2D(leftMost, upMost), Point2D(rightMost, downMost))) {
                (rightMost - leftMost + 1) * (downMost - upMost + 1)
            } else
                -1
        }

    override fun part1(input: InputRepresentation): Long = input
        .lines
        .map { it.split(",").let { (x, y) -> Point2D(x.toLong(), y.toLong()) } }
        .areaOfLargestRectangle()

    override fun part2(input: InputRepresentation): Long = input
        .lines
        .map { it.split(",").let { (x, y) -> Point2D(x.toLong(), y.toLong()) } }
        .let { tiles ->
            // sorting, so all edges are e1.x <= e2.x && e1.y <= e2.y, since either | or - edges
            val edges = (tiles + tiles.subList(0, 1)).windowed(2).map { it.sorted() }
            tiles.areaOfLargestRectangle { leftUpperCorner, rightLowerCorner ->
                edges.none { (leftOrTop, rightOrBottom) ->
                    // x is outside the edge
                    leftUpperCorner.x < rightOrBottom.x && rightLowerCorner.x > leftOrTop.x &&
                        // y is outside the edge
                        leftUpperCorner.y < rightOrBottom.y && rightLowerCorner.y > leftOrTop.y
                }
            }
        }
}

fun main() = Day09.run()
