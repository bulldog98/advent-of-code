package year2025

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

    data class Machine(
        val lightIndicators: List<Boolean>,
        val buttons: List<Set<Long>>,
        val joltageRequirements: List<Long>,
    ) {
        companion object {
            fun parse(line: String): Machine {
                val split = line.split(" ")
                return Machine(
                    split.first().drop(1).dropLast(1).map {
                        when (it) {
                            '.' -> false
                            '#' -> true
                            else -> error("unable to parse $it")
                        }
                    },
                    split.drop(1).dropLast(1).map { it.toAllLongs().toSet() },
                    split.last().toAllLongs().toList()
                )
            }
        }
    }

    override fun part1(input: InputRepresentation): Long = input
        .lines
        .mapIndexed { i, it ->
            val machine = Machine.parse(it)
            with(KContext()) {
                val buttonPresses = List(machine.buttons.size) { index ->
                    mkConst("buttonPressCount$index", intSort)
                }
                val lightIndicators = List(machine.lightIndicators.size) { index ->
                    mkConst("lightIndicator$index", intSort)
                }
                KZ3Solver(this).use { solver ->
                    machine.lightIndicators.forEachIndexed { index, value ->
                        val boolAsInt = if (value) 1 else 0
                        solver.assertAndTrack(lightIndicators[index] eq boolAsInt.expr)
                    }
                    buttonPresses.forEach { indicator ->
                        solver.assertAndTrack(indicator ge 0.expr)
                    }
                    lightIndicators.forEachIndexed { light, indicator ->
                        val buttonsChangingLight = buttonPresses
                            .filterIndexed { index, _ ->
                                light.toLong() in machine.buttons[index]
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
        }.sum()

    override fun part2(input: InputRepresentation): Long = input
        .lines
        .map(Machine::parse)
        .sumOf { machine ->
            with(KContext()) {
                val buttonPresses = List(machine.buttons.size) { index ->
                    mkConst("buttonPressCount$index", intSort)
                }
                val joltages = List(machine.joltageRequirements.size) { index ->
                    mkConst("joltageRequirement$index", intSort)
                }
                KZ3Solver(this).use { solver ->
                    buttonPresses.forEach { indicator ->
                        solver.assertAndTrack(indicator ge 0.expr)
                    }
                    machine.joltageRequirements.forEachIndexed { index, value ->
                        solver.assertAndTrack(joltages[index] eq value.expr)
                    }
                    joltages.forEachIndexed { joltage, joltageVariable ->
                        val buttonsIncreasingJoltage = buttonPresses
                            .filterIndexed { index, _ ->
                                joltage.toLong() in machine.buttons[index]
                            }
                            .fold((0.expr as KExpr<KIntSort>)) { acc, buttonCount ->
                                acc + buttonCount
                            }
                        solver.assertAndTrack(joltageVariable eq buttonsIncreasingJoltage)
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
}

fun main() = Day10.run()