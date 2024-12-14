package year2023

import adventday.AdventDay
import year2023.day22.Brick
import year2023.day22.Brick.Companion.settle

object Day22 : AdventDay(2023, 22) {
    override fun part1(input: List<String>): Any {
        val settledBricks = Brick.of(input).settle()
        return settledBricks.count { brick ->
            val otherBricks = settledBricks.filter { it != brick }
            otherBricks.all { it.isSupportedWithOutBricks(brick) }
        }
    }

    override fun part2(input: List<String>): Int {
        val settledBricks = Brick.of(input).settle().sortedBy { it.points.first().z }
        return settledBricks.filter {brick ->
            val otherBricks = settledBricks.filter { it != brick }
            otherBricks.any { !it.isSupportedWithOutBricks(brick) }
        }.sumOf { brick ->
            var otherBricks = settledBricks.filter { it != brick }.map { it.copy(supportedBy = it.supportedBy - brick) }
            var lastRound: List<Brick>
            do {
                lastRound = otherBricks
                val unsupported = otherBricks.filter { !it.supported }.map { it.points }.toSet()
                otherBricks = otherBricks.map {
                    it.copy(supportedBy = it.supportedBy.filter { b -> b.points !in unsupported })
                }
            } while (otherBricks != lastRound)
            otherBricks.count { !it.supported }
        }
    }
}

fun main() = Day22.run()
