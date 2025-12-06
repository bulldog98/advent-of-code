package year2024

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import helper.numbers.toAllLongs
import io.ksmt.KContext
import io.ksmt.expr.KInt32NumExpr
import io.ksmt.expr.KInt64NumExpr
import io.ksmt.solver.KSolverStatus
import io.ksmt.solver.z3.KZ3Solver
import io.ksmt.utils.getValue
import kotlin.time.Duration.Companion.seconds

val pointRegex = """X\+(\d+), Y\+(\d+)""".toRegex()

data class ClawMachineConfiguration(
    val buttonAMovement: Point2D,
    val buttonBMovement: Point2D,
    val prizeLocation: Point2D
) {
    operator fun Long.times(point2D: Point2D) = Point2D(this * point2D.x, this * point2D.y)

    fun minCostToWinOrNull(): Long? = with(KContext()) {
        val aPresses by intSort
        val bPresses by intSort

        KZ3Solver(this).use { solver ->
            solver.assert(
                aPresses * buttonAMovement.x.expr +
                    bPresses * buttonBMovement.x.expr
                    eq prizeLocation.x.expr
            )
            solver.assert(
                aPresses * buttonAMovement.y.expr +
                    bPresses * buttonBMovement.y.expr
                    eq prizeLocation.y.expr
            )
            solver.assert(aPresses ge (0L).expr)
            solver.assert(bPresses ge (0L).expr)
            solver.assert(aPresses * (3L).expr + bPresses ge (0L).expr)
            solver.check(timeout = 15.seconds).let {
                if (it != KSolverStatus.SAT)
                    null
                else {
                    val aPressResult = solver.model().eval(aPresses).let { aPressesValue ->
                        (aPressesValue as? KInt32NumExpr)?.value?.toLong() ?: (aPressesValue as KInt64NumExpr).value
                    }
                    val bPressResult = solver.model().eval(bPresses).let { bPressesValue ->
                        (bPressesValue as? KInt32NumExpr)?.value?.toLong() ?: (bPressesValue as KInt64NumExpr).value
                    }
                    aPressResult * 3 + bPressResult
                }
            }
        }
    }

    companion object {
        private fun String.parsePointMovement(): Point2D = pointRegex.find(this)?.destructured?.let { (x, y) ->
            Point2D(x.toLong(), y.toLong())
        } ?: error("has to have x and y")

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

object Day13 : AdventDay(2024, 13, "Claw Contraption") {
    override fun part1(input: InputRepresentation) = input.asSplitByEmptyLine()
        .map { ClawMachineConfiguration.parse(it.lines) }
        .sumOf { it.minCostToWinOrNull() ?: 0L }

    override fun part2(input: InputRepresentation): Long = input.asSplitByEmptyLine()
        .map { ClawMachineConfiguration.parse(it.lines) }
        .map { it.copy(prizeLocation = it.prizeLocation + Point2D(10000000000000, 10000000000000)) }
        .sumOf { it.minCostToWinOrNull() ?: 0L }
}

fun main() = Day13.run()