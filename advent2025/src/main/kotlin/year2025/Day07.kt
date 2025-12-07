package year2025

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import findAllPositionsOf
import year2025.day07.TachyonManifold

private const val splitter = '^'

object Day07 : AdventDay(2025, 7, "Laboratories") {
    override fun part1(input: InputRepresentation): Int = mutableSetOf<Point2D>().also { splittersHit ->
        TachyonManifold(input.lines.findAllPositionsOf(splitter))
            .simulateFrom(
                input.lines.findAllPositionsOf('S').first(),
                splittersHit::add
            )
    }.size

    override fun part2(input: InputRepresentation): Long =
        TachyonManifold(input.lines.findAllPositionsOf(splitter))
            .simulateFrom(
                input.lines.findAllPositionsOf('S').first()
            )
            .values
            .sum()
}

fun main() = Day07.run()
