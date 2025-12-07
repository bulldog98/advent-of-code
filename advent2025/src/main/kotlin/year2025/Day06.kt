package year2025

import adventday.AdventDay
import adventday.InputRepresentation
import year2025.day06.Operator

private fun InputRepresentation.extractColumns(): List<List<String>> {
    val columnSizes = lines.last().split("*", "+")
        .drop(1)
        .dropLast(1)
        .map { it.length }
    val tableAsRows = lines.dropLast(1).map { line ->
        buildList {
            var offset = 0
            columnSizes.forEach { size ->
                this += line.substring(offset, offset + size)
                offset += size + 1
            }
            this += line.substring(offset)
        }
    }
    return tableAsRows[0].indices.map { column ->
        tableAsRows.indices.map { row ->
            tableAsRows[row][column]
        }
    }
}

private fun InputRepresentation.asRawColumns(
    parseColumn: (List<String>) -> List<Long> = { it.map { column -> column.trim().toLong() } }
): List<Pair<Operator, List<Long>>> {
    val instructions = lines.last().split(" ").filter { it.isNotEmpty() }
        .map { Operator.parse(it) }
    return instructions.zip(extractColumns().map { column -> parseColumn(column) })
}

object Day06 : AdventDay(2025, 6, "Trash Compactor") {
    override fun part1(input: InputRepresentation): Long = input
        .asRawColumns()
        .sumOf { (operator, cells) ->
            operator.compute(cells)
        }

    override fun part2(input: InputRepresentation): Long = input
        .asRawColumns {
            // Cephalopod math is written right-to-left in columns
            val maxPosition = it.maxOf { cell -> cell.length } - 1
            (0..maxPosition).map { index ->
                it.joinToString("") { cell -> (cell.getOrNull(index) ?: ' ').toString() }.trim().toLong()
            }
        }
        .sumOf { (operator, cells) ->
            operator.compute(cells)
        }
}

fun main() = Day06.run()