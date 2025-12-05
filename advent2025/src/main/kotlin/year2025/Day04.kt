package year2025

import adventday.AdventDay
import adventday.InputRepresentation
import findAllPositionsOf

private const val paperRoll = '@'

object Day04 : AdventDay(2025, 4) {
    override fun part1(input: InputRepresentation): Int {
        val positions = input.lines.findAllPositionsOf(paperRoll)
        return positions.count { position ->
            position.neighborHood.count { it in positions } < 4
        }
    }

    override fun part2(input: InputRepresentation): Int {
        val positions = input.lines.findAllPositionsOf(paperRoll)
        val allAccessiblePaperRollsRemoved = generateSequence(positions) { currentPositions ->
            currentPositions.filter { position ->
                position.neighborHood.count { it in currentPositions } >= 4
            }.toSet()
        }.zipWithNext()
            .first { (a, b) -> a == b }
            .first
        return positions.size - allAccessiblePaperRollsRemoved.size
    }
}

fun main() = Day04.run()
