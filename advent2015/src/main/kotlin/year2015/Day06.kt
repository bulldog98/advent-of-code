package year2015

import NotYetImplemented
import Point2D
import adventday.AdventDay
import adventday.InputRepresentation

sealed interface Instruction : (Boolean) -> Boolean {
    data object TurnOn : Instruction {
        override fun invoke(unused: Boolean) = true
    }
    data object TurnOff : Instruction {
        override fun invoke(unused: Boolean) = false
    }
    data object Toggle : Instruction {
        override fun invoke(value: Boolean) = !value
    }
    companion object {
        fun parse(instruction: String) = when (instruction.trim()) {
            "turn on" -> TurnOn
            "turn off" -> TurnOff
            "toggle" -> Toggle
            else -> error("Unknown instruction: $instruction")
        }
    }
}
data class Range(val upperLeft: Point2D, val lowerRight: Point2D) {
    companion object {
        fun parse(input: String) = input.split(" through ").let { (left, right) ->
            Range(left.parsePoint2D(), right.parsePoint2D())
        }

        private fun String.parsePoint2D() = split(",").let { (x, y) ->
            Point2D(x.toLong(), y.toLong())
        }
    }
}

class Grid {
    private val backingArray = Array(1000) { Array(1000) { false } }

    fun apply(instruction: Instruction, range: Range) {
        (range.upperLeft.x .. range.lowerRight.x).forEach { x ->
            (range.upperLeft.y..range.lowerRight.y).forEach { y ->
                backingArray[x.toInt()][y.toInt()] = instruction(backingArray[x.toInt()][y.toInt()])
            }
        }
    }

    fun countTurnedOn(): Int = backingArray.sumOf { row -> row.count { it } }
}


object Day06 : AdventDay(2015, 6, "Probably a Fire Hazard") {
    override fun part1(input: InputRepresentation): Int = input
        .lines
        // transform to instructions
        .map { line ->
            Instruction.parse(line.takeWhile { !it.isDigit() }) to
                Range.parse(line.dropWhile { !it.isDigit() })
        }
        .fold(Grid()) { grid, (instruction, range) ->
            grid.also { it.apply(instruction, range) }
        }.countTurnedOn()

    override fun part2(input: InputRepresentation): Any =
        NotYetImplemented
}

fun main() = Day06.run()
