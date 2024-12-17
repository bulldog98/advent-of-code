package year2024

import adventday.AdventDay
import adventday.InputRepresentation
import helper.numbers.toAllLongs
import helper.pair.mapFirst
import helper.pair.mapSecond
import year2024.day17.ComputerState
import year2024.day17.RegistryState
import year2024.day17.simulateProgram

object Day17 : AdventDay(2024, 17) {
    override fun part1(input: InputRepresentation): String {
        val (initialRegistryState, instructions) = input.asTwoBlocks()
            .mapFirst { RegistryState.parse(it) }
            .mapSecond { it[0].toAllLongs().toList() }
        val output = simulateProgram(
            ComputerState(
                initialRegistryState,
                instructions
            )
        )
        return output.joinToString(",")
    }

    override fun part2(input: InputRepresentation): Long {
        val (initialState, instructionText) = input.asTwoBlocks()
        val initialRegistryState = RegistryState.parse(initialState)
        val instructions = instructionText[0].toAllLongs().toList()
        val range = 8
        TODO("Not yet implemented")
    }
}

fun main() = Day17.run()