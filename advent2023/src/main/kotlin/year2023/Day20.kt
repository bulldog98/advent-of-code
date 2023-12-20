package year2023

import AdventDay

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
        ): Module {
            override val name = "broadcaster"
            override fun acceptPulseFrom(from: String, pulse: Pulse): Module = this

            override fun sendPulse(from: String, pulse: Pulse): Pulse = pulse
        }
    }

    private fun Map<String, Module>.simulateOneButtonPush(): Pair<Map<String, Module>, Pair<Long, Long>> {
        val result = this.toMutableMap()
        var lowPulsesSent = 0L // button push itself
        var highPulsesSent = 0L
        val pulsesToProcess: MutableList<Triple<String, String, Pulse>> = mutableListOf(Triple("button", "broadcaster", Pulse.LowPulse))
        while (pulsesToProcess.isNotEmpty()) {
            val (from, to, pulse) = pulsesToProcess.removeFirst()
            if (pulse == Pulse.LowPulse) {
                lowPulsesSent++
            } else {
                highPulsesSent++
            }
            val module = result[to] ?: continue
            val pulseToSend = module.sendPulse(from, pulse)
            result[to] = module.acceptPulseFrom(from, pulse)
            if (pulseToSend == null) continue
            pulsesToProcess += module.destinationModules.map { Triple(module.name, it, pulseToSend) }
        }
        return result to (lowPulsesSent to highPulsesSent)
    }

    private operator fun Pair<Long, Long>.plus(other: Pair<Long, Long>) = first + other.first to second + other.second

    override fun part1(input: List<String>): Long =
        generateSequence(Module.of(input) to (0L to 0L)) { (currentState, count) ->
            val (nextState, pulsesSent) = currentState.simulateOneButtonPush()
            nextState to pulsesSent + count
        }.drop(1)
            .take(1000)
            .last()
            .second
            .let { (lowPulses, highPulses) ->
                lowPulses * highPulses
            }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day20.run()
