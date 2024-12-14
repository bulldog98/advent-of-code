package year2023

import adventday.AdventDay
import adventday.InputRepresentation
import helper.numbers.parseAllInts

object Day12 : AdventDay(2023, 12) {
    data class Line(val pattern: String, val numRepresentation: List<Int>) {
        companion object {
            fun of(input: String): Line {
                val (first, second) = input.split(" ")
                return Line(first, second.parseAllInts().toList())
            }
        }

        private fun asState() = State(
            pattern.length + 1,
            mask,
            pattern
        )

        private class State(val n: Int, val mask: BooleanArray, val pattern: String, val dpPoint: Array<LongArray>, val dpHash: Array<LongArray>) {
            constructor(n: Int, mask: BooleanArray, pattern: String): this(
                n,
                mask,
                pattern,
                Array(n) { LongArray(mask.size) { 0 } }.also {
                    it[0][0] = 1
                },
                Array(n) { LongArray(mask.size) { 0 } }.also {
                    it[0][0] = 1
                }
            )
            private val m: Int
                get() = mask.size
            private fun considerPoint(i: Int, j: Int) {
                if (!mask[j]) {
                    dpPoint[i][j] += dpPoint[i - 1][j]
                    dpHash[i][j] += dpPoint[i - 1][j]
                }
            }

            private fun considerBroken(i: Int, j: Int) {
                if (mask[j]) {
                    dpPoint[i][j] += dpHash[i - 1][j - 1]
                    dpHash[i][j] += dpHash[i - 1][j - 1]
                } else {
                    dpPoint[i][j] += dpHash[i][j - 1]
                }
            }

            fun computeNumberOfArrangements(): Long {
                for (i in 1 ..< n) {
                    val c = pattern[i - 1]
                    if (c == '.' || c == '?') {
                        dpPoint[i][0] = dpPoint[i - 1][0]
                        dpHash[i][0] = dpHash[i - 1][0]
                    }

                    for (j in 1 ..< m) {
                        when(c) {
                            '.' -> {
                                considerPoint(i, j)
                            }
                            '#' -> {
                                considerBroken(i, j)
                            }
                            '?' -> {
                                considerPoint(i, j)
                                considerBroken(i, j)
                            }
                        }
                    }
                }

                return dpPoint.last().last()
            }
        }

        private val mask: BooleanArray by lazy {
            val sum = numRepresentation.sum()
            val n = numRepresentation.size

            val mask = BooleanArray(n + sum + 1) { false }
            var ctr = 0
            for (i in 0 ..< n) {
                repeat(numRepresentation[i]) {
                    ++ctr
                    mask[ctr] = true
                }
                ++ctr
            }

            mask
        }

        fun computeNumberOfArrangements() =
            asState().computeNumberOfArrangements()
    }

    private fun <T> List<T>.repeatList(n: Int): List<T> = buildList {
        repeat(n) {
            addAll(this@repeatList)
        }
    }

    override fun part1(input: InputRepresentation): Long =
        input
            .map(Line::of)
            .sumOf { it.computeNumberOfArrangements() }


    override fun part2(input: InputRepresentation): Long =
        input
            .map { line ->
                val (pattern, nums) = line.split(" ")
                val newPattern = listOf(pattern).repeatList(5).joinToString("?") { it }
                val newNums = listOf(nums).repeatList(5).joinToString(",") { it }
                "$newPattern $newNums"
            }.map(Line::of)
            .sumOf {
                it.computeNumberOfArrangements()
            }
}

fun main() = Day12.run()
