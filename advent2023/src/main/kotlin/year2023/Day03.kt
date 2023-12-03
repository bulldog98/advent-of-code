package year2023

import AdventDay
import Point2D

sealed interface Data
data class DataNumber(val num: Int, val positions: List<Point2D>): Data
data class DataSymbol(val symbol: Char): Data

object Day03 : AdventDay(2023, 3) {
    override fun part1(input: List<String>): Any {
        val board = parseInput(input)
        val nums = board.entries
            .filter {
                it.value is DataSymbol
            }.flatMap {
                it.key.neighborHood
                    .mapNotNull { pos -> board[pos] }
                    .filterIsInstance<DataNumber>()
                    .toSet()
            }
        return nums.sumOf { it.num }
    }

    private fun parseInput(input: List<String>) = input.asSequence().withIndex().flatMap { (y, line) ->
        val digits = "(\\d)+".toRegex().findAll(line).flatMap {
            val positions = it.range.map { x -> Point2D(x = x, y = y) }
            val number = DataNumber(it.value.toInt(), positions)
            positions.map { pos -> pos to number }
        }
        val chars = line.withIndex()
            .filter { !it.value.isDigit() && it.value != '.' }
            .map { (x, c) -> Point2D(x = x, y = y) to DataSymbol(c) }
        digits + chars
    }.toMap()

    override fun part2(input: List<String>): Any {
        val board = parseInput(input)
        return board.entries
            .map {
                it.value to it.key.neighborHood
                    .mapNotNull { pos -> board[pos] }
                    .filterIsInstance<DataNumber>()
                    .toSet()
            }.filter { (value, surroundingNumbers) ->
                value is DataSymbol && value.symbol == '*' && surroundingNumbers.size == 2
            }.sumOf { (_, surroundings) ->
                val (num1, num2) = surroundings.toList()
                num1.num * num2.num
            }
    }
}

fun main() = Day03.run()
