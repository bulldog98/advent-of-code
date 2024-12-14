package year2022

import adventday.AdventDay
import adventday.InputRepresentation

private typealias Move = Triple<Int, Int, Int>

data class Field(val cols: List<List<Char>>, val pickup: (List<Char>, Int) -> List<Char>): List<List<Char>> by cols {
    val result: String
        get() = cols.joinToString("") { it[0].toString() }

    fun makeMove(move: Move): Field {
        val column = this[move.second]
        val moved = pickup(column, move.first)
        val newColumn = column.drop(move.first)
        return copy(
            cols = cols.mapIndexed { index, chars ->
                when (index) {
                    move.second -> newColumn
                    move.third -> moved + cols[index]
                    else -> chars
                }
            }
        )
    }
}

private fun String.parseMove(): Move {
    val (a, b, c) = split(" ").mapNotNull { it.toIntOrNull() }
    // fixup count starts with 0
    return Triple(a, b - 1, c - 1)
}

private fun List<String>.parseOpenField(pickup: (List<Char>, Int) -> List<Char>): Field {
    val size = this.last().split(" ").mapNotNull { it.toIntOrNull() }.last() - 1
    val result = (0..size).map { mutableListOf<Char>() }.toMutableList()
    this.reversed().drop(1).map {
        it.chunked(4)
    }.forEach {
        it.map { str ->
            str.getOrNull(1)
        }.forEachIndexed { index, c ->
            if (c != null && c != ' ') {
                result[index] += c
            }
        }
    }
    return Field(result.map { it.reversed() }, pickup)
}

private fun computeInstructions(input: List<String>, pickup: (List<Char>, Int) -> List<Char>): Field {
    val start = input.takeWhile { it.isNotBlank() }
    val field = start.parseOpenField(pickup)
    val instructions = input.drop(start.size + 1).map { it.parseMove() }
    val result = instructions.fold(field) { f, m ->
        f.makeMove(m)
    }
    return result
}

class Day05 : AdventDay(2022, 5) {
    override fun part1(input: InputRepresentation): String {
        val result = computeInstructions(input) { column, i ->
            column.take(i).reversed()
        }
        return result.result
    }

    override fun part2(input: InputRepresentation): String {
        val result = computeInstructions(input) { column, i ->
            column.take(i)
        }
        return result.result
    }
}

fun main() = Day05().run()