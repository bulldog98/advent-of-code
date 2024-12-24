package year2024

import adventday.AdventDay
import adventday.InputRepresentation
import helper.pair.mapFirst
import helper.pair.mapSecond

object Day24 : AdventDay(2024, 24) {
    private sealed interface Gate {
        val variable: String
        val dependsOn: List<String>
        fun compute(variables: Map<String, Boolean>): Boolean

        data class XorGate(val param1: String, val param2: String, override val variable: String) : Gate {
            override val dependsOn: List<String>
                get() = listOf(param1, param2)

            override fun compute(variables: Map<String, Boolean>): Boolean =
                (variables[param1]!! xor variables[param2]!!)
        }

        data class OrGate(val param1: String, val param2: String, override val variable: String) : Gate {
            override val dependsOn: List<String>
                get() = listOf(param1, param2)

            override fun compute(variables: Map<String, Boolean>): Boolean =
                (variables[param1]!! || variables[param2]!!)
        }

        data class AndGate(val param1: String, val param2: String, override val variable: String) : Gate {
            override val dependsOn: List<String>
                get() = listOf(param1, param2)

            override fun compute(variables: Map<String, Boolean>): Boolean =
                (variables[param1]!! && variables[param2]!!)
        }

        companion object {
            fun parse(line: String): Gate {
                val (var1, var2, result) = line.split(" AND ", " XOR ", " OR ", " -> ")
                return when {
                    "AND" in line -> AndGate(var1, var2, result)
                    "XOR" in line -> XorGate(var1, var2, result)
                    "OR" in line -> OrGate(var1, var2, result)
                    else -> error("cannot parse gate from $line")
                }
            }
        }
    }

    private data class Wireing(
        val initialValues: Map<String, Boolean>,
        val gates: List<Gate>
    ) {
        fun computeValues(): String {
            val resultGatesInCorrectOrder = gates.filter { it.variable.startsWith('z') }
                .sortedBy { it.variable }
                .reversed()
            val variableResult = resultGatesInCorrectOrder.fold(this) { wireing, nextGate ->
                wireing.computeValue(nextGate.variable)
            }.initialValues
            return resultGatesInCorrectOrder.joinToString("") {
                val result = variableResult[it.variable]
                when (result) {
                    true -> "1"
                    false -> "0"
                    else -> error("variable not computed")
                }
            }
        }

        private fun computeValue(value: String): Wireing = when {
            value in initialValues.keys -> this
            gates.any { it.variable == value } -> {
                val gate = gates.first { it.variable == value }
                val wireing = gate
                    .dependsOn
                    .fold(this) { w, variable ->
                        w.computeValue(variable)
                    }
                wireing.copy(
                    initialValues = initialValues + (value to gate.compute(wireing.initialValues))
                )
            }

            else -> error("should not happen")
        }

        companion object {
            fun parse(input: InputRepresentation): Wireing {
                val (variables, gates) = input.asTwoBlocks()
                    .mapFirst { lines ->
                        buildMap {
                            lines.forEach { line ->
                                val (name, value) = line.split(": ")
                                put(
                                    name, when (value) {
                                        "1" -> true
                                        "0" -> false
                                        else -> error("cannot parse $value to Boolean")
                                    }
                                )
                            }
                        }
                    }
                    .mapSecond { lines -> lines.map { Gate.parse(it) } }
                return Wireing(variables, gates)
            }
        }
    }

    override fun part1(input: InputRepresentation): Long {
        val wireing = Wireing.parse(input)
        return wireing.computeValues().toLong(2)
    }

    override fun part2(input: InputRepresentation): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day24.run()