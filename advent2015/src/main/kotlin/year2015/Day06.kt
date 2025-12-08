package year2015

import adventday.AdventDay
import adventday.InputRepresentation
import year2015.day06.Range

typealias Instruction = (Int) -> Int

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
        .computeBrightnessWithParser {
            when (it.trim()) {
                "turn on" -> { _ -> 1 }
                "turn off" -> { _ -> 0 }
                "toggle" -> { brightness -> if (brightness == 0) 1 else 0 }
                else -> error("Unknown instruction: $it")
            }
        }

    override fun part2(input: InputRepresentation): Int = input
        .computeBrightnessWithParser {
            when (it.trim()) {
                "turn on" -> { brightness -> brightness + 1 }
                "turn off" -> { brightness -> (brightness - 1).coerceAtLeast(0) }
                "toggle" -> { brightness -> brightness + 2 }
                else -> error("Unknown instruction: $it")
            }
        }
}

fun main() = Day06.run()
