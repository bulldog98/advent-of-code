package year2019.computer

import adventday.InputRepresentation
import helper.numbers.toAllLongs
import year2019.computer.instruction.HaltInstruction
import year2019.computer.instruction.Instruction
import year2019.computer.instruction.InstructionContext
import year2019.computer.instruction.getInstruction

class IntComputer private constructor(
    initialMemory: List<Long>,
    private val handleOutput: (Long) -> Unit,
    private val handleInput: () -> Long
) {
    private val memory = initialMemory.toMutableList()
    private var instructionPointer = 0
    private val instructionContext by lazy {
        InstructionContext(memory, handleInput, handleOutput) {
            instructionPointer = it.toInt()
        }
    }

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
        val (instruction, parameters) = memory[instructionPointer].splitInInstructionAndItsParameters(
            memory.toList(),
            instructionPointer
        )
        if (instruction != HaltInstruction) {
            // first increase the pointer, so that if the instruction jumps it overrides the pointer
            instructionPointer += 1 + instruction.numberOfParameters
            with(instructionContext) {
                with(instruction) {
                    // strange workaround with `with`, since otherwise the receiver does not work and the call can not be resolved
                    executeWith(parameters)
                }
            }
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
        fun parse(
            input: InputRepresentation,
            handleOutput: (Long) -> Unit = { println(it) },
            handleInput: () -> Long = {
                println("Input")
                readln().toLong()
            }
        ) = IntComputer(input.flatMap { it.toAllLongs() }, handleOutput, handleInput)

        fun parse(
            input: String,
            handleOutput: (Long) -> Unit = { println(it) },
            handleInput: () -> Long = {
                println("Input")
                readln().toLong()
            }
        ) = IntComputer(input.toAllLongs().toList(), handleOutput, handleInput)

        fun parseAsFunction(vararg computerInstructions: Long): (Long) -> Long = { input ->
            val output = mutableListOf<Long>()
            val outputFunction: (Long) -> Unit = { output += it }
            val computer = IntComputer(computerInstructions.toList(), outputFunction) { input }
            computer.simulateUntilHalt()
            output.single()
        }

        fun parseAsFunction(computerInstructions: String): (Long) -> Long =
            parseAsFunction(*computerInstructions.toAllLongs().toList().toLongArray())

        private fun Long.splitInInstructionAndItsParameters(
            memory: List<Long>,
            instructionPointer: Int
        ): Pair<Instruction, List<Long>> {
            val opCode = this % 100
            val instruction = getInstruction(opCode)
            val digitsWithoutOpCodeFromRightToLeft = (this / 100).toString().reversed().asSequence()
            val parameterModes =
                digitsWithoutOpCodeFromRightToLeft.map { ParameterMode.entries[it.digitToInt()] } + generateSequence { ParameterMode.PositionMode }
            return instruction to parameterModes.withIndex()
                .map { (indexMinus1, parameterMode) ->
                    val content = memory[indexMinus1 + 1 + instructionPointer]
                    if (indexMinus1 + 1 in instruction.writesToParameters) {
                        parameterMode.transformWriteParameter(content)
                    } else {
                        parameterMode.transformReadParameter(content, memory)
                    }
                }
                // important, otherwise the list construction fails
                .take(instruction.numberOfParameters)
                .toList()
                .also {
                    assert(it.size == instruction.numberOfParameters) { "wrong number of parameters" }
                }
        }
    }
}