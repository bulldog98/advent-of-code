package year2025

import adventday.AdventDay
import adventday.InputRepresentation

object Day12 : AdventDay(2025, 12, "Christmas Tree Farm") {
    data class Area(val width: Int, val height: Int, val boxIndexToCount: Map<Int, Int>)

    fun String.parseArea(): Area {
        val (dims, numbers) = this.split(": ")
        val (width, height) = dims.split("x").map { it.toInt() }
        return Area(width, height, numbers.split(" ").mapIndexed { index, it -> index to it.toInt() }.toMap())
    }

    override fun part1(input: InputRepresentation): Any = input
        .sections
        .let { sections ->
            val areas = sections.last().lines.map { line -> line.parseArea() }
            val shapeSizes = sections.dropLast(1).map { it.text.count { char -> char == '#' } }
            areas.count { area ->
                // can trivially fit (shapes are all 3x3 with eventually some pieces missing)
                if ((area.height / 3 * 3) * (area.width / 3) * 3 >= area.boxIndexToCount.values.sumOf { it * 9 }) {
                    return@count true
                }
                // cannot possibly fit
                if (area.height * area.width < area.boxIndexToCount.values.mapIndexed { i, it -> it * shapeSizes[i] }.sum()) {
                    return@count false
                }
                TODO("implement real packing algorithm")
            }
        }

    override fun part2(input: InputRepresentation) = "Merry Christmas"
}

fun main() = Day12.run()
