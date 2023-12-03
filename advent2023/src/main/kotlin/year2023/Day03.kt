package year2023

import AdventDay
import Point2D

sealed interface Data
data class DataNumber(val num: Int, val positions: List<Point2D>): Data
data class DataSymbol(val symbol: Char): Data

object Day03 : AdventDay(2023, 3) {
    override fun part1(input: List<String>): Any {
        val board = input.asSequence().withIndex().flatMap { (y, line) ->
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

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day03.run()
