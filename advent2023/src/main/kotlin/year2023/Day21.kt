package year2023

import AdventDay
import findAllPositionsOf

object Day21: AdventDay(2023, 21) {
    override fun part1(input: List<String>): Any {
        val start = input.findAllPositionsOf('S').single()
        val nodes = input.findAllPositionsOf('.') + start
        val possiblePositions = generateSequence(setOf(start)) {
            it.flatMap { n -> n.cardinalNeighbors.filter { neighbor -> neighbor in nodes } }.toSet()
        }
        return possiblePositions.drop(1).take(64).last().size
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day21.run()
