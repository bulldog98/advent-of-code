package year2015

import adventday.AdventDay
import adventday.InputRepresentation
import year2015.day06.Instruction
import year2015.day06.Range

class Grid {
    private val backingArray = Array(1000) { Array(1000) { 0 } }

    fun apply(instruction: Instruction, range: Range) {
        (range.upperLeft.x .. range.lowerRight.x).forEach { x ->
            (range.upperLeft.y..range.lowerRight.y).forEach { y ->
                backingArray[x.toInt()][y.toInt()] = instruction(backingArray[x.toInt()][y.toInt()])
            }
        }
    }

    fun countBrightness(): Int = backingArray.sumOf { row -> row.sumOf { it } }
}


object Day06 : AdventDay(2015, 6, "Probably a Fire Hazard") {
    private fun InputRepresentation.computeBrightnessWithParser(instructionParser: (String) -> Instruction): Int =
        lines
            .map { line ->
                instructionParser(line.takeWhile { !it.isDigit() }) to
                    Range.parse(line.dropWhile { !it.isDigit() })
            }
            .fold(Grid()) { grid, (instruction, range) ->
                grid.also { it.apply(instruction, range) }
            }.countBrightness()

    override fun part1(input: InputRepresentation): Int = input
        .computeBrightnessWithParser(Instruction::parsePart1)

    override fun part2(input: InputRepresentation): Int = input
        .computeBrightnessWithParser(Instruction::parsePart2)
}

fun main() = Day06.run()
