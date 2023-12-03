package year2021

import AdventDay
import year2021.day13.Instructions

object Day13 : AdventDay(2021, 13) {
    override fun part1(input: List<String>): Any {
        val instructions = Instructions(input)
        val firstFoldingInstructions = instructions.folds.first()
        return firstFoldingInstructions.useOn(instructions.currentFolding).points.size
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day13.run()
