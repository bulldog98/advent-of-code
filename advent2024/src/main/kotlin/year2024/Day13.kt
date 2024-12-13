package year2024

import AdventDay
import Point2D
import helper.numbers.toAllLongs
import io.ksmt.KContext
import io.ksmt.expr.KInt64NumExpr
import io.ksmt.solver.KSolverStatus
import io.ksmt.solver.z3.KZ3Solver
import io.ksmt.utils.getValue
import java.math.BigInteger
import kotlin.time.Duration.Companion.seconds

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
                    // a costs 3, b costs 1
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
            assert(input.size == 3) { "three lines are needed" }
            return ClawMachineConfiguration(
                input[0].parsePointMovement(),
                input[1].parsePointMovement(),
                input[2].toAllLongs().toList().let { (x, y) -> Point2D(x, y) },
            )
        }
    }
}

object Day13 : AdventDay(2024, 13) {
    override fun part1(input: List<String>) = input.joinToString("\n")
        .split("\n\n")
        .map { ClawMachineConfiguration.parse(it.lines()) }
        .sumOf { it.minCostToWinOrNull() ?: 0L }

    override fun part2(input: List<String>): BigInteger = input.joinToString("\n")
        .split("\n\n")
        .map { ClawMachineConfiguration.parse(it.lines()) }
        .map { it.copy(prizeLocation = it.prizeLocation + Point2D(10000000000000, 10000000000000)) }
        .fold(BigInteger.ZERO) { acc, clawMachineConfiguration ->
            with(KContext()) {
                val aPresses by intSort
                val bPresses by intSort

                KZ3Solver(this).use { solver ->
                    solver.assert(
                        aPresses * clawMachineConfiguration.buttonAMovement.x.expr +
                            bPresses * clawMachineConfiguration.buttonBMovement.x.expr
                            eq clawMachineConfiguration.prizeLocation.x.expr
                    )
                    solver.assert(
                        aPresses * clawMachineConfiguration.buttonAMovement.y.expr +
                            bPresses * clawMachineConfiguration.buttonBMovement.y.expr
                            eq clawMachineConfiguration.prizeLocation.y.expr
                    )
                    solver.assert(aPresses ge (0L).expr)
                    solver.assert(bPresses ge (0L).expr)
                    solver.assert(aPresses * (3L).expr + bPresses ge (0L).expr)
                    solver.check(timeout = 15.seconds).let {
                        if (it != KSolverStatus.SAT)
                            acc
                        else {
                            val aPressResult = (solver.model().eval(aPresses) as KInt64NumExpr).value
                            val bPressResult = (solver.model().eval(bPresses) as KInt64NumExpr).value
                            acc + aPressResult.toBigInteger() * (3).toBigInteger() + bPressResult.toBigInteger()
                        }
                    }
                }
            }
        }
}

fun main() = Day13.run()