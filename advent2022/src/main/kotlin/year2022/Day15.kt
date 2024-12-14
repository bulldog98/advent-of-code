package year2022

import adventday.AdventDay
import kotlin.math.absoluteValue

private fun manhattenDistance(x: Long, y: Long, foundX: Long, foundY: Long) =
    (x - foundX).absoluteValue + (y - foundY).absoluteValue

class Day15(private val row: Long, private val maxCoordinate: Int) : AdventDay(2022, 15) {

    data class Sensor(val x: Long, val y: Long, val foundX: Long, val foundY: Long) {
        fun safeRangForRow(row: Long): LongRange? {
            val dst = manhattenDistance(x, y, foundX, foundY)
            val dstToY = (y - row).absoluteValue
            val width = dst - dstToY
            if (width > 0) {
                // Add a start/end of safe spot indexes
                return (x - width..x + width)
            }
            return null
        }

        companion object {
            fun of(input: String): Sensor {
                val (a, b) = input.split(":")
                val (x1, y1) = a.split(",")
                val (x) = x1.split("=").mapNotNull { it.toLongOrNull() }
                val (y) = y1.split("=").mapNotNull { it.toLongOrNull() }
                val (x2, y2) = b.split(",")
                val (foundX) = x2.split("=").mapNotNull { it.toLongOrNull() }
                val (foundY) = y2.split("=").mapNotNull { it.toLongOrNull() }
                return Sensor(x, y, foundX, foundY)
            }
        }
    }

    data class Area(val sensors: List<Sensor>) {
        private val maxXOfSet = sensors.maxOf { sensors.maxOf { s -> (s.x - s.foundX).absoluteValue } }
        private val minX = sensors.minOf {
            listOf(it.x - maxXOfSet, it.foundX, 0).min()
        }
        private val maxX = sensors.maxOf { listOf(it.x + maxXOfSet, it.foundX, 0).max() }
        fun countNonDistressSignalInRow(row: Long, mX: Long = minX, maX: Long = maxX): Int =
            (mX..maX).filter {  x ->
                sensors.any { s ->
                    manhattenDistance(s.x, s.y, s.foundX, s.foundY) >= manhattenDistance(x, row, s.x, s.y) &&
                            !(s.foundX == x && s.foundY == row)
                }
            }.size

        fun safeRangForRow(row: Long): List<LongRange> = sensors.mapNotNull {
                s -> s.safeRangForRow(row)
        }

        companion object {
            fun of(input: List<String>): Area = Area(input.map { Sensor.of(it) })
        }
    }

    override fun part1(input: List<String>): Int {
        val area = Area.of(input)
        return area.countNonDistressSignalInRow(row)
    }
    override fun part2(input: List<String>): Long {
        val area = Area.of(input)
        val safeRangesPerLine = Array<List<LongRange>>(maxCoordinate + 1) { mutableListOf() }

        for (row in 0..maxCoordinate) {
            safeRangesPerLine[row] = area.safeRangForRow(row.toLong())
        }

        safeRangesPerLine.forEachIndexed { y, ranges ->
            val sortedRanges = ranges.sortedBy { it.first }
            var highest = sortedRanges.first().last

            // Find first set of ranges with a gap
            sortedRanges.drop(1).forEach {
                if (it.first > highest) {
                    return (it.first - 1) * 4000000 + y
                }
                if (it.last > highest) {
                    highest = it.last
                }
            }
        }

        return -1
    }
}

fun main() = Day15(2_000_000, 4_000_000).run()
