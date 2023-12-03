package year2021

import AdventDay
import year2021.day14.SimulationState

object Day14 : AdventDay(2021, 14) {
    override fun part1(input: List<String>) =
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

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}
fun main() = Day14.run()
