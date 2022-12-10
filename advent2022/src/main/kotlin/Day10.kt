import kotlin.math.absoluteValue

class Day10 : AdventDay(2022, 10) {
    class Screen(
        val rows: Array<BooleanArray> =
            (0 until 6)
                .map { BooleanArray(40) { false } }
                .toTypedArray()
    ) {
        fun print() {
            rows.forEach { row ->
                val range = (0..39)
                println(range.joinToString("") { if (row[it]) "#" else "."})
            }
        }
    }
    sealed class Move(val tick: Int) {
        companion object {
            fun from(input: String): Move = when {
                input == "noop" -> Noop
                input.startsWith("addx ") -> AddX(input.split(" ")[1].toInt())
                else -> error("should not happen")
            }
        }
    }
    object Noop : Move(1) {
        override fun toString(): String = "Noop"
    }
    class AddX(val v: Int) : Move(2) {
        override fun toString(): String = "AddX($v)"
    }
    data class State(val program: List<Move>, val x: Int = 1, val cycle: Int = 0, val addXRunning: Boolean = false) {
        fun tick() = when (val c = program.firstOrNull()) {
            is Noop -> copy(cycle = cycle + 1, program = program.drop(1))
            is AddX -> if (addXRunning) {
                copy(
                    x = x + c.v,
                    cycle = cycle + 1,
                    addXRunning = false,
                    program = program.drop(1)
                )
            } else {
                copy(
                    cycle = cycle + 1,
                    addXRunning = true,
                )
            }
            null -> this
        }
    }
    override fun part1(input: List<String>): Int {
        val program = input.map { Move.from(it) }
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

    override fun part2(input: List<String>): Int {
        val program = input.map { Move.from(it) }
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
        screen.print()
        return -1
    }
}

fun main() = Day10().run()