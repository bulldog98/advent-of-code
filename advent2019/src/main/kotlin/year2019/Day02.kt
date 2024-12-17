package year2019

import adventday.AdventDay
import adventday.InputRepresentation
import helper.numbers.toAllLongs

data class IntComputerExecutionState(
    val program: List<Long>,
    val position: Int
) {
    companion object {
        fun parse(input: InputRepresentation) = IntComputerExecutionState(
            program = input.flatMap { it.toAllLongs() },
            0
        )
    }
}

fun IntComputerExecutionState.applyOpCode1(): IntComputerExecutionState = copy(
    program = program.replace(program[position + 3].toInt(), program[program[position + 1].toInt()] + program[program[position + 2].toInt()]),
    position = position + 4
)

fun IntComputerExecutionState.applyOpCode2(): IntComputerExecutionState = copy(
    program = program.replace(program[position + 3].toInt(), program[program[position + 1].toInt()] * program[program[position + 2].toInt()]),
    position = position + 4
)

fun List<Long>.replace(position: Int, value: Long) =
    subList(0, position) + value + subList(position + 1, size)

fun IntComputerExecutionState.simulateUntilHalt() = generateSequence(this) { state ->
    // println(state)
    val opCode = state.program[state.position]
    when (opCode) {
        1L -> state.applyOpCode1()
        2L -> state.applyOpCode2()
        99L -> null
        else -> error("")
    }
}.last()

object Day02: AdventDay(2019, 2) {
    override fun part1(input: InputRepresentation): Long {
        val start = IntComputerExecutionState.parse(input).run {
            copy(program = program.replace(1, 12).replace(2, 2))
        }

        return start.simulateUntilHalt().program[0]
    }

    override fun part2(input: InputRepresentation): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day02.run()