package year2015

import adventday.AdventDay
import adventday.InputRepresentation
import year2015.day07.Instruction
import year2015.day07.asInstructions

// 16 bit unsigned -> UShort
object Day07 : AdventDay(2015, 7, "Some Assembly Required") {
    private fun Map<String, UShort>.canEvaluateVariableOrInt(variableOrInt: String) =
        variableOrInt.all { it.isDigit() } || this.containsKey(variableOrInt)

    private fun Map<String, UShort>.canEvaluateInstruction(instruction: Instruction): Boolean = when {
        instruction is Instruction.FixedWire -> canEvaluateVariableOrInt(instruction.valueOrNumber)
        instruction is Instruction.And -> canEvaluateVariableOrInt(instruction.left) && canEvaluateVariableOrInt(instruction.right)
        instruction is Instruction.Or -> canEvaluateVariableOrInt(instruction.left) && canEvaluateVariableOrInt(instruction.right)
        instruction is Instruction.LeftShift -> canEvaluateVariableOrInt(instruction.left)
        instruction is Instruction.RightShift -> canEvaluateVariableOrInt(instruction.left)
        instruction is Instruction.Not -> canEvaluateVariableOrInt(instruction.variable)
        else -> error("Unhandled case $instruction")
    }

    fun Map<String, Instruction>.evaluateWire(wire: String): UShort {
        val cache = mutableMapOf<String, UShort>()
        val notEvaluated = keys.toMutableList()
        while (notEvaluated.isNotEmpty()) {
            val toEvaluate = notEvaluated.first { cache.canEvaluateInstruction(this[it]!!) }
            notEvaluated -= toEvaluate
            cache[toEvaluate] = this[toEvaluate]!!.invoke(cache)
        }
        return cache[wire] ?: 0u
    }

    override fun part1(input: InputRepresentation): UShort = input
        .asInstructions
        .evaluateWire("a")

    override fun part2(input: InputRepresentation): UShort = input
        .asInstructions
        .let { lookup ->
            val firstIterationWireA = lookup.evaluateWire("a")
            val newInstructions = lookup + ("b" to Instruction.FixedWire(firstIterationWireA.toString()))
            newInstructions.evaluateWire("a")
        }
}

fun main() = Day07.run()
