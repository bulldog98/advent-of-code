package year2024

import AdventDay
import Point2D
import helper.numbers.toAllLongs

val xRegex = """X\+(\d+)""".toRegex()
val yRegex = """Y\+(\d+)""".toRegex()

data class ClawMachineConfiguration(
    val buttonAMovement: Point2D,
    val buttonBMovement: Point2D,
    val prizeLocation: Point2D
) {
    operator fun Long.times(point2D: Point2D) = Point2D(this * point2D.x, this * point2D.y)

    fun minCostToWinOrNull(): Long? = sequence {
        val maxXMoves = minOf(buttonAMovement.x, buttonBMovement.x)
        val maxYMove = minOf(buttonAMovement.y, buttonBMovement.y)
        val maxMoves = maxOf(prizeLocation.y / maxYMove + 1, prizeLocation.x / maxXMoves + 1)
        (0..maxMoves).forEach { buttonAPresses ->
            (0..maxMoves).forEach { buttonBPresses ->
                if (buttonAPresses * buttonAMovement + buttonBPresses * buttonBMovement == prizeLocation) {
                    yield(3 * buttonAPresses + buttonBPresses)
                }
            }
        }
    }.minOrNull()

    companion object {
        private fun String.parsePointMovement(): Point2D {
            val x = xRegex.find(this)?.let { it.groupValues[1].toLong() } ?: error("not found x")
            val y = yRegex.find(this)?.let { it.groupValues[1].toLong() } ?: error("not found y")
            return Point2D(x, y)
        }

        // three lines
        fun parse(input: List<String>): ClawMachineConfiguration {
            assert(input.size == 3) { "three lines are needed "}
            return ClawMachineConfiguration(
                input[0].parsePointMovement(),
                input[1].parsePointMovement(),
                input[2].toAllLongs().toList().let { (x, y) -> Point2D(x, y) },
            )
        }
    }
}

object Day13 : AdventDay(2024, 13) {
    override fun part1(input: List<String>): Long {
        val configs = input.joinToString("\n").split("\n\n")
            .map { ClawMachineConfiguration.parse(it.lines()) }
        // a costs 3, b costs 1
        return configs.sumOf { it.minCostToWinOrNull() ?: 0L }
    }

    override fun part2(input: List<String>): Long {
        TODO("Not yet implemented")
    }
}

fun main() = Day13.run()