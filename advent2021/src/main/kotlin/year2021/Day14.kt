package year2021

import adventday.AdventDay
import adventday.InputRepresentation
import year2021.day14.SimulationState

object Day14 : AdventDay(2021, 14) {
    override fun part1(input: InputRepresentation) =
        SimulationState(input.asTwoBlocks())
            .simulate()
            .first { it.afterStep == 10 }
            .computeDifference()

    override fun part2(input: InputRepresentation) =
        SimulationState(input.asTwoBlocks())
            .simulate()
            .first { it.afterStep == 40 }
            .computeDifference()
}
fun main() = Day14.run()
