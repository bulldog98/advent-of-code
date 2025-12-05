package year2023

import adventday.AdventDay
import adventday.InputRepresentation
import lcm

object Day20 : AdventDay(2023, 20) {
    private sealed interface Pulse {
        data object HighPulse : Pulse
        data object LowPulse : Pulse
    }

    private sealed interface Module {
        val name: String
        val destinationModules: List<String>
        fun acceptPulseFrom(from: String, pulse: Pulse): Module
        fun sendPulse(from: String, pulse: Pulse): Pulse?

        companion object {
            fun of(input: String): Module {
                val (part1, part2) = input.split(" -> ")
                val destinationModules = part2.split(", ")
                return when {
                    part1 == "broadcaster" -> BroadCasterModule(destinationModules)
                    part1.startsWith("%") -> FlipFlopModule(part1.drop(1), destinationModules)
                    part1.startsWith("&") -> ConjunctionModule(part1.drop(1), destinationModules, mapOf())
                    else -> error("unknown module with description $input")
                }
            }

            fun of(input: List<String>): Map<String, Module> =
                buildMap {
                    input.forEach {
                        val mod = of(it)
                        this[mod.name] = mod
                    }
                    this["rx"] = RxModule()
                    this.forEach { moduleEntry ->
                        val module = moduleEntry.value
                        if (module is ConjunctionModule) {
                            val inputModules = this.filter { module.name in it.value.destinationModules }.values.map {
                                it.name
                            }
                            this[moduleEntry.key] = module.copy(
                                rememberedPulse = inputModules.associateWith { Pulse.LowPulse }
                            )
                        }
                    }
                }
        }

        data class FlipFlopModule(
            override val name: String,
            override val destinationModules: List<String>,
            val state: Boolean = false,
        ) : Module {
            override fun acceptPulseFrom(from: String, pulse: Pulse): Module = when (pulse) {
                Pulse.LowPulse -> copy(state = !state)
                Pulse.HighPulse -> copy()
            }

            override fun sendPulse(from: String, pulse: Pulse): Pulse? = when (pulse) {
                Pulse.HighPulse -> null
                Pulse.LowPulse -> if (state) Pulse.LowPulse else Pulse.HighPulse
            }
        }

        data class ConjunctionModule(
            override val name: String,
            override val destinationModules: List<String>,
            val rememberedPulse: Map<String, Pulse>,
        ) : Module {
            override fun acceptPulseFrom(from: String, pulse: Pulse): Module = copy(
                rememberedPulse = rememberedPulse + mapOf(from to pulse)
            )

            override fun sendPulse(from: String, pulse: Pulse): Pulse =
                if (pulse == Pulse.HighPulse && rememberedPulse.all { it.key == from || it.value == Pulse.HighPulse })
                    Pulse.LowPulse
                else
                    Pulse.HighPulse
        }

        data class BroadCasterModule(
            override val destinationModules: List<String>
        ) : Module {
            override val name = "broadcaster"
            override fun acceptPulseFrom(from: String, pulse: Pulse): Module = this

            override fun sendPulse(from: String, pulse: Pulse): Pulse = pulse
        }

        data class RxModule(val lowReceived: Boolean = false) : Module {
            override val name: String = "rx"
            override val destinationModules: List<String> = listOf()
            override fun acceptPulseFrom(from: String, pulse: Pulse): Module =
                copy(lowReceived = lowReceived || pulse == Pulse.LowPulse)

            override fun sendPulse(from: String, pulse: Pulse): Pulse? = null
        }
    }

    private fun Map<String, Module>.simulateOneButtonPush(doSomethingWithPulses: (Triple<String, String, Pulse>) -> Unit): Map<String, Module> {
        val result = this.toMutableMap()
        val pulsesToProcess: MutableList<Triple<String, String, Pulse>> =
            mutableListOf(Triple("button", "broadcaster", Pulse.LowPulse))
        while (pulsesToProcess.isNotEmpty()) {
            val pulseTriple = pulsesToProcess.removeFirst()
            doSomethingWithPulses(pulseTriple)
            val (from, to, pulse) = pulseTriple
            val module = result[to] ?: continue
            val pulseToSend = module.sendPulse(from, pulse)
            result[to] = module.acceptPulseFrom(from, pulse)
            if (pulseToSend == null) continue
            pulsesToProcess += module.destinationModules.map { Triple(module.name, it, pulseToSend) }
        }
        return result
    }

    private operator fun Pair<Long, Long>.plus(other: Pair<Long, Long>) = first + other.first to second + other.second

    override fun part1(input: InputRepresentation): Long =
        generateSequence(Module.of(input.lines) to (0L to 0L)) { (currentState, count) ->
            var lowCount = 0L
            var highCount = 0L
            val nextState = currentState.simulateOneButtonPush { (_, _, pulse) ->
                if (pulse is Pulse.LowPulse) lowCount++ else highCount++
            }
            nextState to (lowCount to highCount) + count
        }.drop(1)
            .take(1000)
            .last()
            .second
            .let { (lowPulses, highPulses) ->
                lowPulses * highPulses
            }

    override fun part2(input: InputRepresentation): Long {
        val modules = Module.of(input.lines)
        val rxInputConjunctionModule = modules.filterValues { "rx" in it.destinationModules }.values.single()
        // my input had lv as conjunction module that outputs into rx. Might not work if your input does not have the same
        if (rxInputConjunctionModule !is Module.ConjunctionModule) error("input of rx must be conjunction module otherwise computation fails")
        val rxInputModules = modules.filterValues { rxInputConjunctionModule.name in it.destinationModules }.map { it.key }
        val history = generateSequence(Triple(modules, mapOf<String, List<Int>>(), 1)) { (state, history, round) ->
            val nextHistory = history.toMutableMap()
            Triple(state.simulateOneButtonPush { (from, _, pulse) ->
                if (from in rxInputModules && pulse is Pulse.HighPulse) {
                    nextHistory[from] = (nextHistory[from] ?: emptyList()) + round
                }
            }, nextHistory, round + 1)
        }.first { (_, history) ->
            history.size == rxInputModules.size && history.values.all { it.size >= 2 }
        }.second
        // in my input the first ping was cycle length
        return lcm(history.map { it.value.first().toLong() })
    }
}

fun main() = Day20.run()
