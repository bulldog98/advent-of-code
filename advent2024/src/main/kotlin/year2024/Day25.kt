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
        val (locks, keys) = blocks.map { block ->
            val lines = block.lines()
            val isKey = lines[0][0] == '.'
            lines[0].indices.map { x ->
                lines.indices.count { h -> lines[h][x] == '#' } - 1
            } to isKey
        }.partition { it.second }
            .mapFirst { list -> list.map { it.first } }
            .mapSecond { list -> list.map { it.first } }

        return locks.sumOf { lock ->
            keys.count { key -> lock.isOpenedBy(key, lockHeight) }.toLong()
        }
    }

    // day 25 only has one task that requires solving, the second is collecting all stars and press a button
    override fun part2(input: InputRepresentation): String = "Happy Christmas"
}

fun main() = Day25.run()