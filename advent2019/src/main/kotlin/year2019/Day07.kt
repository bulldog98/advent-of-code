package year2019

import adventday.AdventDay
import adventday.InputRepresentation
import year2019.computer.IntComputer

object Day07 : AdventDay(2019, 7) {
    private fun <E> List<E>.allPermutations(): Sequence<List<E>> = when {
        isEmpty() -> emptySequence()
        size == 1 -> sequenceOf(listOf(single()))
        else -> asSequence().flatMap { element ->
            (this - element).allPermutations().map { listOf(element) + it }
        }
    }

    private fun InputRepresentation.createAmplifierControllerSoftware(amplifierSetting: Long): (Long) -> Long {
        val inputs = mutableListOf(amplifierSetting)
        val output = mutableListOf<Long>()
        val amplifierControllerSoftware = IntComputer.parse(this, output::add, inputs::removeFirst)
        return {
            inputs += it
            amplifierControllerSoftware.simulateUntilHalt()
            output.single()
        }
    }

    override fun part1(input: InputRepresentation): Long {
        val allPhaseSettings = listOf(0L, 1, 2, 3, 4)
        return allPhaseSettings.allPermutations().maxOf { phaseSettingConfiguration ->
            phaseSettingConfiguration.map { input.createAmplifierControllerSoftware(it) }.fold(0L) { input, function ->
                function(input)
            }
        }
    }

    override fun part2(input: InputRepresentation): Long {
        TODO("Not yet implemented")
    }
}

fun main() = Day07.run()