package year2025

import NotYetImplemented
import adventday.AdventDay
import adventday.InputRepresentation
import helper.numbers.toAllLongs
import io.ksmt.KContext
import io.ksmt.expr.KApp
import io.ksmt.expr.KExpr
import io.ksmt.solver.KModel
import io.ksmt.solver.KSolverStatus
import io.ksmt.solver.z3.KZ3Solver
import io.ksmt.sort.KIntSort
import kotlin.time.Duration.Companion.seconds

object Day10 : AdventDay(2025, 10, "Factory") {
    private fun KContext.generateAssertForLightConfigurations(
        configuration: List<Boolean>,
        lightIndicators: List<KApp<KIntSort, *>>,
        solver: KZ3Solver
    ) {
        configuration.forEachIndexed { index, value ->
            val boolAsInt = if (value) 1 else 0
            solver.assertAndTrack(lightIndicators[index] eq boolAsInt.expr)
        }
    }

    private fun KContext.evaluateNumberOfButtonPresses(
        model: KModel,
        buttonPresses: List<KApp<KIntSort, *>>
    ): Long = model.eval(sumButtonPresses(buttonPresses)).toString().toLong()

    private fun KContext.sumButtonPresses(buttonPresses: List<KApp<KIntSort, *>>): KExpr<KIntSort> =
        buttonPresses.fold((0.expr as KExpr<KIntSort>)) { acc, numberVar ->
            acc + numberVar
        }

    override fun part1(input: InputRepresentation): Long = input
        .lines
        .mapIndexed { i, it ->
            it.split(" ").let { list ->
                val configuration = list.first().drop(1).dropLast(1).map {
                    when (it) {
                        '.' -> false
                        '#' -> true
                        else -> error("unable to parse $it")
                    }
                }
                val buttons = list.drop(1).dropLast(1).map {
                    it.toAllLongs().toSet()
                }
                with(KContext()) {
                    val buttonPresses = List(buttons.size) { index ->
                        mkConst("buttonPressCount$index", intSort)
                    }
                    val lightIndicators = List(configuration.size) { index ->
                        mkConst("lightIndicator$index", intSort)
                    }
                    KZ3Solver(this).use { solver ->
                        configuration.forEachIndexed { index, value ->
                            val boolAsInt = if (value) 1 else 0
                            solver.assertAndTrack(lightIndicators[index] eq boolAsInt.expr)
                        }
                        buttonPresses.forEach { indicator ->
                            solver.assertAndTrack(indicator ge 0.expr)
                        }
                        lightIndicators.forEachIndexed { light, indicator ->
                            val buttonsChangingLight = buttonPresses
                                .filterIndexed { index, _ ->
                                    light.toLong() in buttons[index]
                                }
                                .fold((0.expr as KExpr<KIntSort>)) { acc, buttonCount ->
                                    acc + buttonCount
                                }
                            solver.assertAndTrack(
                                indicator eq (buttonsChangingLight.rem(2.expr))
                            )
                        }
                        (0..Int.MAX_VALUE).first { testedButtonPresses ->
                            solver.checkWithAssumptions(
                                listOf(sumButtonPresses(buttonPresses) eq testedButtonPresses.expr),
                                1.seconds
                            ) == KSolverStatus.SAT
                        }.toLong()
                    }
                }
            }
        }.sum()

    override fun part2(input: InputRepresentation): Any =
        NotYetImplemented
}

fun main() = Day10.run()