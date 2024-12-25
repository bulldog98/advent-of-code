package year2024

import adventday.AdventDay
import adventday.InputRepresentation
import helper.pair.mapFirst
import helper.pair.mapSecond

object Day25 : AdventDay(2024, 25) {
    private fun List<Int>.isOpenedBy(key: List<Int>, lockHeight: Int): Boolean = zip(key)
        .all { (l, k) -> l <= lockHeight - k }

    override fun part1(input: InputRepresentation): Long {
        val blocks = input.asSplitByEmptyLine()
        val lockHeight = blocks[0].lines().size - 2 // top and bottom always full or empty
        val (locks, keys) = blocks.groupBy { it[0] == '.' }.values.toList()
            .let { it[0] to it[1] }
            .mapFirst {
                it.map { block ->
                    val lines = block.lines()
                    val heights = lines.indices
                    lines[0].indices.map { x ->
                        heights.count { h -> lines[h][x] == '#' } - 1
                    }
                }
            }.mapSecond {
                it.map { block ->
                    val lines = block.lines()
                    val heights = lines.indices
                    lines[0].indices.map { x ->
                        heights.count { h -> lines[h][x] == '#' } - 1
                    }
                }
            }
        return locks.sumOf { lock ->
            keys.count { key -> lock.isOpenedBy(key, lockHeight) }.toLong()
        }
    }

    // day 25 only has one task that requires solving, the second is collecting all stars and press a button
    override fun part2(input: InputRepresentation): String = "Happy Christmas"
}

fun main() = Day25.run()