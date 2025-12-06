package year2025

import adventday.AdventDay
import adventday.InputRepresentation
import helper.numbers.toAllLongs

object Day06 : AdventDay(2025, 6) {
    override fun part1(input: InputRepresentation): Long {
        val table = input.lines.dropLast(1).map {
            it.toAllLongs().toList()
        }
        val instructions = input.lines.last().split(" ").filter { it.isNotEmpty() }
            .mapIndexed { i, t -> i to t }
        return instructions.sumOf { (i, op) -> when (op) {
            "+" -> table.sumOf { it[i] }
            "*" -> table.fold(1) { acc, next -> acc * next[i] }
            else -> error("should not happen")
        } }
    }

    override fun part2(input: InputRepresentation): Long {
        TODO("Not yet implemented")
    }
}

fun main() = Day06.run()