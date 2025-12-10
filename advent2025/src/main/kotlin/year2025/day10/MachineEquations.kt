package year2025.day10

import io.ksmt.KContext
import io.ksmt.expr.KExpr
import io.ksmt.solver.KSolverStatus
import io.ksmt.solver.z3.KZ3Solver
import io.ksmt.sort.KIntSort
import kotlin.time.Duration.Companion.seconds

data class MachineEquations(
    val context: KContext,
    val machine: Machine,
) {
    val lightIndicatorVariables by lazy {
        with(context) {
            List(machine.lightIndicators.size) { index ->
                mkConst("lightIndicator$index", intSort)
            }
        }
    }
    val buttonVariables by lazy {
        with(context) {
            List(machine.buttons.size) { index ->
                mkConst("buttonPressCount$index", intSort)
            }
        }
    }
    @Suppress("SpellCheckingInspection")
    val joltageVariables by lazy {
        with(context) {
            List(machine.joltageRequirements.size) { index ->
                mkConst("joltageRequirement$index", intSort)
            }
        }
    }
    val countButtonPresses by lazy {
        with(context) {
            buttonVariables.fold((0.expr as KExpr<KIntSort>)) { acc, numberVar ->
                acc + numberVar
            }
        }
    }

    fun addButtonGeRestrictions(solver: KZ3Solver) = with(context) {
        buttonVariables.forEach { indicator ->
            solver.assertAndTrack(indicator ge 0.expr)
        }
    }

    fun addLightIndicatorResultEquations(solver: KZ3Solver) = with(context) {
        machine.lightIndicators.forEachIndexed { index, value ->
            val boolAsInt = if (value) 1 else 0
            solver.assertAndTrack(lightIndicatorVariables[index] eq boolAsInt.expr)
        }
    }

    @Suppress("SpellCheckingInspection")
    fun addJoltageRestrictions(solver: KZ3Solver) = with(context) {
        machine.joltageRequirements.forEachIndexed { index, requirement ->
            solver.assertAndTrack(joltageVariables[index] eq requirement.expr)
        }
    }

    fun computeMinimumAmountOfButtonPresses(solver: KZ3Solver) = with(context) {
        (0..Int.MAX_VALUE).first { testedButtonPresses ->
            solver.checkWithAssumptions(
                listOf(countButtonPresses eq testedButtonPresses.expr),
                1.seconds
            ) == KSolverStatus.SAT
        }.toLong()
    }

    fun buttonsAffectingEquation(index: Long) = with(context) {
        buttonVariables.filterIndexed { idx, _ ->
            index in machine.buttons[idx]
        }.fold((0.expr as KExpr<KIntSort>)) { acc, buttonCount ->
            acc + buttonCount
        }
    }
}