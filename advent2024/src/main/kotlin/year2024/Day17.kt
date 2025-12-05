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
            .mapFirst { RegistryState.parse(it.lines) }
            .mapSecond { it.lines[0].toAllLongs().toList() }
        val output = simulateProgram(
            ComputerState(
                initialRegistryState,
                instructions
            )
        )
        return output.joinToString(",")
    }

    /*
     * this solution uses the fact that the prefix fixing the last x digits gives us the prefixes fixing the last x + 1
     * digits by appending a new digit
     */
    override fun part2(input: InputRepresentation): Long {
        val (initialRegistryState, instructions) = input.asTwoBlocks()
            .mapFirst { RegistryState.parse(it.lines) }
            .mapSecond { it.lines[0].toAllLongs().toList() }
        val baseComputation = ComputerState(initialRegistryState, instructions)

        val result = instructions.indices.fold(listOf("")) { prefixesFixingXLastDigits, lastXDigitsFixed ->
            prefixesFixingXLastDigits.flatMap { prefix ->
                (0..7L).map { "$prefix$it" }
                    .filter { number ->
                        val res = simulateProgram(baseComputation.overrideRegisterA(number.toLong(8)))
                        res.takeLast(lastXDigitsFixed + 1) == instructions.takeLast(lastXDigitsFixed + 1)
                    }
            }
        }
        return result[0].toLong(8)
    }
}

fun main() = Day17.run()