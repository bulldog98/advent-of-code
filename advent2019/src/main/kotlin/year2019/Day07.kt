package year2019

import adventday.AdventDay
import adventday.InputRepresentation
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
            runBlocking {
                amplifierControllerSoftware.simulateUntilHalt()
            }
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

    private fun InputRepresentation.setupComputer(
        name: Int,
        inputChannel: Channel<Long>,
        outputChannel: Channel<Long>,
        debugOutput: (String) -> Unit
    ) =
        IntComputer.parseWithSuspendInputOutput(this, {
            debugOutput("Computer $name tries to receive")
            inputChannel.receive().also {
                debugOutput("Computer $name received")
            }
        }) {
            debugOutput("Computer $name tries to send")
            outputChannel.send(it)
            debugOutput("Computer $name sends: $it")
        }

    override fun part2(input: InputRepresentation): Long {
        val allPhaseSettings = (5..9L).toList()
        return allPhaseSettings.allPermutations().maxOf { phaseSettingConfiguration ->
            runBlocking {
                val debugOutput: (String) -> Unit = {}
                val (phaseSetting1, phaseSetting2, phaseSetting3, phaseSetting4, phaseSetting5) = phaseSettingConfiguration
                val channels = (0..4).map { Channel<Long>() }
                val computers = (0..4).map { input.setupComputer(it + 1, channels[it], channels[(it + 1) % 5], debugOutput) }

                val monitoredExecutions = (0..3).map { launch { computers[it].simulateUntilHaltWithInterruptions() } }
                launch { computers[4].simulateUntilHaltWithInterruptions() }

                // region start configuring computers
                launch {
                    debugOutput("configure first computer")
                    channels[0].send(phaseSetting1)
                    debugOutput("configured first computer")
                }
                launch {
                    debugOutput("configure second computer")
                    channels[1].send(phaseSetting2)
                    debugOutput("configured second computer")
                }
                launch {
                    debugOutput("configure third computer")
                    channels[2].send(phaseSetting3)
                    debugOutput("configured third computer")
                }
                launch {
                    debugOutput("configure fourth computer")
                    channels[3].send(phaseSetting4)
                    debugOutput("configured fourth computer")
                }
                launch {
                    debugOutput("configure fifth computer")
                    channels[4].send(phaseSetting5)
                    debugOutput("configured fifth computer")
                    debugOutput("sent initial value")
                    channels[0].send(0)
                }
                // endregion

                runBlocking {
                    (0..3).forEach {
                        monitoredExecutions[it].join()
                        debugOutput("computer ${it + 1} finished")
                    }
                }
                channels[0].receive()
            }
        }
    }
}

fun main() = Day07.run()