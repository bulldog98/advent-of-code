package year2019.computer

import adventday.InputRepresentation
import helper.numbers.toAllLongs
import year2019.computer.instruction.HaltInstruction
import year2019.computer.instruction.getInstruction

class IntComputer(initialMemory: List<Long>) {
    private val memory = initialMemory.toMutableList()
    private var instructionPointer = 0

    /**
     * get the address
     */
    operator fun get(address: Long) = memory[address.toInt()]

    /**
     * override address with value
     */
    operator fun set(address: Long, value: Long) {
        memory[address.toInt()] = value
    }

    private fun getInstruction() = getInstruction(this[instructionPointer.toLong()])

    private fun computeOneStep() {
        val instruction = getInstruction()
        if (instruction != HaltInstruction) {
            instruction(memory, (1..instruction.numberOfParameters.toLong()).map { this[it + instructionPointer] })
            instructionPointer += 1 + instruction.numberOfParameters
        }
    }

    private fun isHalted() = getInstruction() == HaltInstruction

    fun simulateUntilHalt(): IntComputer {
        while (!isHalted()) {
            computeOneStep()
        }
        return this
    }

    companion object {
        fun parse(input: InputRepresentation) = IntComputer(input.flatMap { it.toAllLongs() })
    }
}