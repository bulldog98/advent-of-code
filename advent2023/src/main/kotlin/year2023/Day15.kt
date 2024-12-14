package year2023

import adventday.AdventDay

object Day15: AdventDay(2023, 15) {
    private fun String.hashWithAppendix1A(): Int {
        var res = 0
        forEach { c ->
            res = ((res + c.code) * 17) % 256
        }
        return res
    }
    data class Lens(val label: String, val lensPower: Int)

    sealed interface Instruction {
        val label: String
        data class Dash(override val label: String): Instruction
        data class Equals(override val label: String, val lensPower: Int): Instruction

        companion object {
            fun of(input: String) = when {
                "=" in input -> Equals(input.substringBefore("="), input.substringAfter("=").toInt())
                else -> Dash(input.substringBefore("-"))
            }
        }
    }

    override fun part1(input: List<String>): Int {
        return input[0].split(",").sumOf { it.hashWithAppendix1A() }
    }

    override fun part2(input: List<String>): Any {
        val instructions = input[0].split(",").map {
            Instruction.of(it)
        }
        val boxes = Array(256) { mutableListOf<Lens>() }
        instructions.forEach { inst ->
            val hash = inst.label.hashWithAppendix1A()
            when (inst) {
                is Instruction.Equals -> {
                    val lens = Lens(inst.label, inst.lensPower)
                    val index = boxes[hash].indexOfFirst { it.label == inst.label }
                    if (index > -1)
                        boxes[hash][index] = lens
                    else
                        boxes[hash] += lens
                }
                is Instruction.Dash ->
                    boxes[hash].removeIf { it.label == inst.label }
            }
        }
        return boxes.flatMapIndexed { i: Int, box: MutableList<Lens> ->
            box.mapIndexed { j, lens -> (1 + i) * (1 + j) * lens.lensPower }
        }.sum()
    }
}

fun main() = Day15.run()
