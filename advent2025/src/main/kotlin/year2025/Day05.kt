package year2025

import adventday.AdventDay
import adventday.InputRepresentation
import helper.pair.mapFirst
import helper.pair.mapSecond

private fun InputRepresentation.toRangesAndIngredients(): Pair<List<LongRange>, List<Long>> = asTwoBlocks()
    .mapFirst {
        it.lines.map { line: String ->
            val (i, j) = line.split("-")
            i.toLong()..j.toLong()
        }.sortedBy { it.first }
    }
    .mapSecond { line ->
        line.lines.map { it: String -> it.toLong() }
    }

private val LongRange.size: Long
    get() = last - start + 1

object Day05 : AdventDay(2025, 5, "Cafeteria") {
    override fun part1(input: InputRepresentation): Int {
        val (ranges, rest) = input.toRangesAndIngredients()
        return rest.count { ranges.any { range -> it in range } }
    }

    // use fold and sortedList
    override fun part2(input: InputRepresentation): Long = input
        .toRangesAndIngredients()
        .first
        .fold(emptyList<LongRange>()) { acc, next ->
            val previous = acc.lastOrNull() ?: return@fold listOf(next)
            when { // A: previous, B: next
                // [A...] [B...] they do not touch
                next.first > previous.last -> acc.plusElement(next)
                // [A---B==)... overlapping or touching
                next.last > previous.last -> acc.dropLast(1).plusElement(previous.first..next.last)
                // B completely within A
                else -> acc
            }
        }
        .sumOf {
            it.size
        }

}

fun main() = Day05.run()
