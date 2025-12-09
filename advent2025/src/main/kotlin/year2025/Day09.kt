package year2025

import NotYetImplemented
import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import collections.pairings

object Day09 : AdventDay(2025, 9, "Movie Theater") {
    override fun part1(input: InputRepresentation): Long = input
        .lines
        .map { it.split(",").let { (x, y) -> Point2D(x.toLong(), y.toLong()) } }
        .let { positions ->
            positions
                .pairings()
                .maxOf { (a, b) ->
                    val leftMost = listOf(a, b).minBy { it.x }
                    val rightMost = listOf(a, b).maxBy { it.x }
                    val upMost = listOf(a, b).minBy { it.y }
                    val downMost = listOf(a, b).maxBy { it.y }
                    (rightMost.x - leftMost.x + 1) * (downMost.y - upMost.y + 1)
                }
        }

    override fun part2(input: InputRepresentation): Any =
        NotYetImplemented
}

fun main() = Day09.run()
