package year2025

import adventday.AdventDay
import adventday.InputRepresentation
import io.ksmt.KContext
import io.ksmt.solver.z3.KZ3Solver
import year2025.day10.Machine
import year2025.day10.MachineEquations

object Day10 : AdventDay(2025, 10, "Factory") {
    override fun part1(input: InputRepresentation): Long = input
        .lines
        .map(Machine::parse)
        .sumOf { machine ->
            with(KContext()) {
                val machineEquations = MachineEquations(this, machine)
                KZ3Solver(this).use { solver ->
                    machineEquations.addButtonGeRestrictions(solver)
                    machineEquations.addLightIndicatorResultEquations(solver)

                    machineEquations.lightIndicatorVariables.forEachIndexed { light, indicator ->
                        val buttonsChangingLight = machineEquations.buttonsAffectingEquation(light.toLong())
                        solver.assertAndTrack(
                            indicator eq (buttonsChangingLight.rem(2.expr))
                        )
                    }
                    machineEquations.computeMinimumAmountOfButtonPresses(solver)
                }
            }
        }

    override fun part2(input: InputRepresentation): Long = input
        .lines
        .map(Machine::parse)
        .sumOf { machine ->
            with(KContext()) {
                val machineEquations = MachineEquations(this, machine)
                KZ3Solver(this).use { solver ->
                    machineEquations.addButtonGeRestrictions(solver)
                    machineEquations.addJoltageRestrictions(solver)
                    @Suppress("SpellCheckingInspection")
                    machineEquations.joltageVariables.forEachIndexed { joltage, joltageVariable ->
                        @Suppress("SpellCheckingInspection")
                        val buttonsIncreasingJoltage = machineEquations.buttonsAffectingEquation(joltage.toLong())
                        solver.assertAndTrack(joltageVariable eq buttonsIncreasingJoltage)
                    }
                    machineEquations.computeMinimumAmountOfButtonPresses(solver)
                }
            }
        }
}

fun main() = Day10.run()