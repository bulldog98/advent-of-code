package year2025

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation

private val paperRoll = '@'

object Day04 : AdventDay(2025, 4) {
    override fun part1(input: InputRepresentation): Int {
        val positions = buildSet {
            for (x in input[0].indices) {
                for (y in input.indices) {
                    if (input[x][y] == paperRoll) {
                        add(Point2D(x, y))
                    }
                }
            }
        }
        return positions.count {
            it.neighborHood.count { it in positions } < 4
        }
    }

    override fun part2(input: InputRepresentation): Long {
        TODO("Not yet implemented")
    }
}

fun main() = Day04.run()
