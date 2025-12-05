package year2022

import adventday.AdventDay
import adventday.InputRepresentation
import kotlin.math.absoluteValue

class Day10 : AdventDay(2022, 10) {
    class Screen(
        val rows: Array<BooleanArray> =
            (0 until 6)
                .map { BooleanArray(40) { false } }
                .toTypedArray()
    ) {
        override fun toString(): String =
            rows.joinToString("\n") { row ->
                val range = (0..39)
                range.joinToString("") { if (row[it]) "#" else "."}
            }
    }
    sealed class Instruction(val cyclesNeeded: Int) {
        companion object {
            fun from(input: String): Instruction = when {
                input == "noop" -> Noop
                input.startsWith("addx ") -> AddX(input.split(" ")[1].toInt())
                else -> error("should not happen")
            }
        }
    }
    data object Noop : Instruction(1)
    class AddX(val v: Int) : Instruction(2) {
        override fun toString(): String = "AddX($v)"
    }
    data class State(val program: List<Instruction>, val x: Int = 1, val cycle: Int = 0, val commandAlreadyRunningForCycles: Int = 0) {
        fun tick() = when (val c = program.firstOrNull()) {
            is Noop -> copy(cycle = cycle + 1, program = program.drop(1))
            is AddX -> if (commandAlreadyRunningForCycles == c.cyclesNeeded - 1) {
                copy(
                    x = x + c.v,
                    cycle = cycle + 1,
                    commandAlreadyRunningForCycles = 0,
                    program = program.drop(1)
                )
            } else {
                copy(
                    cycle = cycle + 1,
                    commandAlreadyRunningForCycles = commandAlreadyRunningForCycles + 1,
                )
            }
            null -> this
        }
    }

    override fun part1(input: InputRepresentation): Int {
        val program = input.lines.map { it: String -> Instruction.from(it) }
        var state = State(program)
        var result = 0
        while (state.cycle < 220) {
            val next = state.tick()
            if (next == state) {
                break
            }
            if (next.cycle % 40 == 20) {
                result += next.cycle * state.x
            }
            state = next
        }
        return result
    }

    override fun part2(input: InputRepresentation): String {
        val program = input.lines.map { it: String -> Instruction.from(it) }
        val screen = Screen()
        var state = State(program)
        while (state.cycle < 240) {
            val next = state.tick()
            val row = state.cycle / 40
            val pixel = state.cycle % 40
            if ((state.x - pixel).absoluteValue in (-1..1)) {
                screen.rows[row][pixel] = true
            }
            state = next
        }
        return screen.toString()
    }
}

fun main() = Day10().run()