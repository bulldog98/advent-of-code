package year2023

import AdventDay

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

    private fun List<String>.findHorizontalReflection(): HorizontalReflection? {
        val potentialRows = indices.filter { it + 1 in indices && this[it] == this[it + 1] }
        return potentialRows.firstOrNull {
            (0..(size / 2)).all {  delta ->
                if (it + 1 + delta in indices && it - delta in indices) {
                    this[it - delta] == this[it + 1 + delta]
                } else {
                    true
                }
            }
        }?.let { x -> HorizontalReflection(x + 1, x + 2) }
    }

    private fun List<String>.columnsEqual(c1: Int, c2: Int) =
        all { it[c1] == it[c2] }

    private fun List<String>.findVerticalReflection(): VerticalReflection? {
        val potentialColumns = this[0].indices.filter { x -> x + 1 in this[0].indices && this.all { it[x] == it[x + 1] } }
        return potentialColumns.firstOrNull {
            (0..(this[0].length / 2)).all {  delta ->
                if (it + 1 + delta in this[0].indices && it - delta in this[0].indices) {
                    this.columnsEqual(it - delta, it + 1 + delta)
                } else {
                    true
                }
            }
        }?.let { x -> VerticalReflection(x + 1, x + 2) }
    }

    private fun List<String>.findReflection(): Reflection =
        findHorizontalReflection() ?: findVerticalReflection() ?: error("could not find reflection")

    override fun part1(input: List<String>): Any {
        val parsed = input.joinToString("\n").split("\n\n").asSequence().map { it.lines() }
        return parsed.sumOf {
            val reflection = it.findReflection()
            reflection.value.toLong()
        }
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day13.run()
