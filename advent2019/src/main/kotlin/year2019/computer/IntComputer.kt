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
     * get the addresses
     */
    operator fun get(addresses: LongRange) = addresses.map { address -> memory[address.toInt()] }

    /**
     * override address with value
     */
    operator fun set(address: Long, value: Long) {
        memory[address.toInt()] = value
    }

    fun computeOneStep(): IntComputer {
        val opCodeEncoding = this[instructionPointer.toLong()]
        val instruction = getInstruction(opCodeEncoding % 100)
        if (instruction != HaltInstruction) {
            val writtenParameters = instruction.writesToParameters
            val parameters =  (1..instruction.numberOfParameters.toLong()).map { parameterNumber ->
                val parameterModeCode = opCodeEncoding.toString().dropLast(1 + parameterNumber.toInt()).lastOrNull()?.digitToInt() ?: 0
                val parameterMode = ParameterMode.entries[parameterModeCode]
                if (parameterNumber.toInt() in writtenParameters) {
                    if (parameterMode != ParameterMode.PositionMode) error("parameters that get written to can only be in position mode")
                    this[parameterNumber + instructionPointer]
                } else {
                    parameterMode.transformParameter(this[parameterNumber + instructionPointer], memory)
                }
            }
            instruction(memory, parameters)
            instructionPointer += 1 + instruction.numberOfParameters
        }
        return this
    }

    private fun isHalted() = this[instructionPointer.toLong()] % 100 == 99L

    fun simulateUntilHalt(): IntComputer {
        while (!isHalted()) {
            computeOneStep()
        }
        return this
    }

    companion object {
        fun parse(input: InputRepresentation) = IntComputer(input.flatMap { it.toAllLongs() })
        fun parse(input: String) = IntComputer(input.toAllLongs().toList())
    }
}