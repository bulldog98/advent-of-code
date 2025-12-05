package year2025

import adventday.AdventDay
import adventday.InputRepresentation
import kotlin.math.max
import kotlin.math.min

private fun InputRepresentation.toRangesAndIngredients(): Pair<List<LongRange>, List<Long>> =
    asSplitByEmptyLine().let { (a, b) ->
        a.lines().map {
            val (i, j) = it.split("-")
            i.toLong()..j.toLong()
        } to b.lines().map { it.toLong() }
    }

fun LongRange.overlap(other: LongRange) = !(last < other.first || other.last < first)

fun LongRange.merge(other: LongRange) = min(other.first, first)..max(other.last, last)

object Day05 : AdventDay(2025, 5) {
    override fun part1(input: InputRepresentation): Int {
        val (ranges, rest) = input.toRangesAndIngredients()
        return rest.count { ranges.any { range -> it in range } }
    }

    override fun part2(input: InputRepresentation): Long {
        val (ranges) = input.toRangesAndIngredients()
        val sortedRanges = ranges.sortedBy { it.first }
        val mergedRanges = sortedRanges.toMutableSet()
        while (true) {
            val rangeA = mergedRanges.firstOrNull {
                mergedRanges.any { other -> other != it && it.overlap(other) }
            } ?: break
            val rangeB = mergedRanges.first { it != rangeA && it.overlap(rangeA) }
            mergedRanges.remove(rangeA)
            mergedRanges.remove(rangeB)
            mergedRanges.add(rangeA.merge(rangeB))
        }
        return mergedRanges.sumOf {
            it.last - it.first + 1L
        }
    }
}

fun main() = Day05.run()
