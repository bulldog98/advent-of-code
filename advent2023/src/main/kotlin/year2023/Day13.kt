package year2023

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation

object Day13: AdventDay(2023, 13) {
    sealed interface Reflection {
        val value: Int
    }
    data class HorizontalReflection(val row1: Int, val row2: Int): Reflection {
        override val value: Int
            get() = 100 * row1
    }
    data class VerticalReflection(val column1: Int, val column2: Int): Reflection {
        override val value: Int
            get() = column1
    }

    private fun List<String>.findHorizontalReflection(): List<HorizontalReflection> {
        val potentialRows = indices.filter { it + 1 in indices && this[it] == this[it + 1] }
        return potentialRows.filter {
            (0..(size / 2)).all {  delta ->
                if (it + 1 + delta in indices && it - delta in indices) {
                    this[it - delta] == this[it + 1 + delta]
                } else {
                    true
                }
            }
        }.map { x -> HorizontalReflection(x + 1, x + 2) }
    }

    private fun List<String>.columnsEqual(c1: Int, c2: Int) =
        all { it[c1] == it[c2] }

    private fun List<String>.findVerticalReflection(): List<VerticalReflection> {
        val potentialColumns = this[0].indices.filter { x -> x + 1 in this[0].indices && this.all { it[x] == it[x + 1] } }
        return potentialColumns.filter {
            (0..(this[0].length / 2)).all {  delta ->
                if (it + 1 + delta in this[0].indices && it - delta in this[0].indices) {
                    this.columnsEqual(it - delta, it + 1 + delta)
                } else {
                    true
                }
            }
        }.map { x -> VerticalReflection(x + 1, x + 2) }
    }

    private fun List<String>.findReflections(): List<Reflection> =
        findHorizontalReflection() + findVerticalReflection()

    private fun List<String>.replaceSmudgeAt(point: Point2D): List<String> = mapIndexed { y, line ->
        if (point.y == y.toLong()) {
            line.mapIndexed { x, c ->
                when (point.x) {
                    x.toLong()-> if (c == '.') '#' else '.'
                    else -> c
                }
            }.joinToString("")
        } else
            line
    }

    private fun List<String>.reflectionAfterReplacingSmudge(): List<Reflection> = generateSequence(0 to 0) { (x, y) ->
        when (this[0].length) {
            x + 1 -> {
                if (y + 1 == size) {
                    null
                } else {
                    0 to y + 1
                }
            }
            else -> x + 1 to y
        }
    }.map {
        replaceSmudgeAt(Point2D(it.first, it.second))
    }.map {
        it.findReflections()
    }.firstOrNull {
        it.size > 1 || (it.isNotEmpty() && findReflections() != it)
    } ?: findReflections()

    override fun part1(input: InputRepresentation): Long {
        val parsed = input.asSplitByEmptyLine().asSequence().map { it.lines }
        return parsed.sumOf {
            val reflection = it.findReflections()
            reflection.single().value.toLong()
        }
    }

    override fun part2(input: InputRepresentation): Long {
        val parsed = input.asSplitByEmptyLine().asSequence().map { it.lines }
        return parsed.sumOf {
            val reflection = it.reflectionAfterReplacingSmudge()
            (reflection - it.findReflections().toSet()).single().value.toLong()
        }
    }
}

fun main() = Day13.run()
