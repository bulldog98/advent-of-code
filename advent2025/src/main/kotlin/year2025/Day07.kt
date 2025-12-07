package year2025

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import findAllPositionsOf

private const val splitter = '^'

object Day07 : AdventDay(2025, 7, "Laboratories") {
    override fun part1(input: InputRepresentation): Int {
        val start = input.lines.findAllPositionsOf('S').first()
        val splitters = input.lines.findAllPositionsOf(splitter)
        val height = input.lines.size

        val endPositions = generateSequence(setOf(start) to setOf<Point2D>()) { (lastPositions, splittersHit) ->
            if (lastPositions.first().y == height.toLong()) return@generateSequence null

            lastPositions.flatMap {
                val next = it + Point2D.DOWN
                if (next in splitters)
                    listOf(next + Point2D.LEFT, next + Point2D.RIGHT)
                else
                    listOf(next)
            }.toSet() to splittersHit + lastPositions.map { it + Point2D.DOWN }.filter { it in splitters }
        }
        return endPositions.last().second.size
    }

    override fun part2(input: InputRepresentation): Long {
        val start = input.lines.findAllPositionsOf('S').first()
        val splitters = input.lines.findAllPositionsOf(splitter)
        val height = input.lines.size

        val endPositions = generateSequence(mapOf(start to 1L)) { lastPositions ->
            if (lastPositions.keys.first().y == height.toLong()) return@generateSequence null

            buildMap {
                lastPositions.forEach { (lastPosition, count) ->
                    val next = lastPosition + Point2D.DOWN

                    if (next in splitters) {
                        putIfAbsent(next + Point2D.LEFT, 0L)
                        putIfAbsent(next + Point2D.RIGHT, 0L)
                        this.computeIfPresent(next + Point2D.LEFT) { _, it ->
                            it + count
                        }
                        this.computeIfPresent(next + Point2D.RIGHT) { _, it ->
                            it + count
                        }
                    } else {
                        putIfAbsent(next, 0L)
                        this.computeIfPresent(next) { _, it ->
                            it + count
                        }
                    }
                }
            }
        }
        return endPositions.last().values.sum()
    }
}

fun main() = Day07.run()
