package year2019

import adventday.AdventDay
import adventday.InputRepresentation
import year2019.computer.IntComputer

object Day02: AdventDay(2019, 2) {
    override fun part1(input: InputRepresentation): Long {
        val computer = IntComputer.parse(input).also {
            it[1] = 12
            it[2] = 2
        }

        return computer.simulateUntilHalt()[0]
    }

    sealed interface MemoryValue {
        fun simplified(): MemoryValue = this
    }
    data class ConstMemoryValue(val const: Long): MemoryValue
    data class VariableValue(val variableName: String): MemoryValue
    data class ComputedAddValue(val v1: MemoryValue, val v2: MemoryValue): MemoryValue {
        override fun simplified() = when {
            v1 is ConstMemoryValue && v2 is ConstMemoryValue -> ConstMemoryValue(
                v1.const + v2.const
            )
            else -> copy(v1 = v1.simplified(), v2 = v2.simplified())
        }
    }

    override fun part2(input: InputRepresentation): Long = (0..99L).flatMap { noun ->
        (0..99L).filter { verb ->
            val computer = IntComputer.parse(input)
            computer[1] = noun
            computer[2] = verb
            computer.simulateUntilHalt()[0] == 19690720L
        }.map { verb ->
            100 * noun + verb
        }
    }.firstOrNull() ?: error("no solution found")
}

fun main() = Day02.run()