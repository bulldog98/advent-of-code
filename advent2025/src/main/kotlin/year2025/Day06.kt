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
        return instructions.sumOf { (i, op) ->
            when (op) {
                "+" -> table.sumOf { it[i] }
                "*" -> table.fold(1) { acc, next -> acc * next[i] }
                else -> error("should not happen")
            }
        }
    }

    override fun part2(input: InputRepresentation): Long {
        val columnSizes = input.lines.last().split("*", "+")
            .drop(1)
            .dropLast(1)
            .map { it.length }
        val tableRaw = input.lines.dropLast(1).map { line ->
            buildList {
                var offset = 0
                columnSizes.forEach { size ->
                    this += line.substring(offset, offset + size)
                    offset += size + 1
                }
                this += line.substring(offset)
            }
        }
        val instructions = input.lines.last().split(" ").filter { it.isNotEmpty() }
            .mapIndexed { i, t -> i to t }
        val numsTable = instructions.mapIndexed { i, _ ->
            val relevantColumn = tableRaw.map { it[i] }
            val maxPosition = relevantColumn.maxOf { it.length } - 1
            (0..maxPosition).map { index ->
                relevantColumn.joinToString("") { (it.getOrNull(index) ?: ' ').toString() }.filter { it != ' ' }
                    .toLong()
            }
        }
        return instructions.sumOf { (i, op) ->
            when (op) {
                "+" -> numsTable[i].sum()
                "*" -> numsTable[i].fold(1) { acc, next -> acc * next }
                else -> error("should not happen")
            }
        }
    }
}

fun main() = Day06.run()