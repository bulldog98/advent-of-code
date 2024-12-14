package year2021

import adventday.AdventDay
import adventday.InputRepresentation
import year2021.day14.SimulationState

object Day14 : AdventDay(2021, 14) {
    override fun part1(input: InputRepresentation) =
        SimulationState(input)
            .simulate()
            .first { it.afterStep == 10 }
            .let { finalPolymer ->
                finalPolymer
                    .groupBy { c -> c }
                    .map { it.value.size }
            }.let { occurrences ->
                occurrences.max() - occurrences.min()
            }

    override fun part2(input: InputRepresentation): Any {
        TODO("Not yet implemented")
    }
}
fun main() = Day14.run()
