package year2019.computer

import adventday.InputRepresentation
import helper.numbers.toAllLongs
import kotlinx.coroutines.runBlocking
import year2019.computer.instruction.*

class IntComputer private constructor(
    initialMemory: List<Long>,
    private val handleOutput: suspend (Long) -> Unit,
    private val handleInput: suspend () -> Long,
) {
    private val memory = Memory(initialMemory)
    private var instructionPointer = 0L
    private var relativeBase = 0L
    private val instructionContext by lazy {
        InstructionContext(memory, handleInput, handleOutput, { relativeBase += it }) {
            instructionPointer = it
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

    suspend fun computeOneStep(): IntComputer {
        val (instruction, parameters) = memory[instructionPointer.toInt()].splitInInstructionAndItsParameters(
            memory,
            instructionPointer,
            relativeBase
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

    private fun isHalted() = this[instructionPointer] % 100 == 99L

    fun simulateUntilHalt(): IntComputer {
        runBlocking {
            while (!isHalted()) {
                computeOneStep()
            }
        }
        return this
    }

    suspend fun simulateUntilHaltWithInterruptions() {
        while (!isHalted()) {
            computeOneStep()
        }
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

        private fun parseAsFunction(vararg computerInstructions: Long): (Long) -> Long = { input ->
            val output = mutableListOf<Long>()
            val outputFunction: (Long) -> Unit = { output += it }
            val computer = IntComputer(computerInstructions.toList(), outputFunction) { input }
            runBlocking {
                computer.simulateUntilHalt()
            }
            output.single()
        }

        fun parseAsFunction(computerInstructions: String): (Long) -> Long =
            parseAsFunction(*computerInstructions.toAllLongs().toList().toLongArray())

        private fun parseWithSuspendInputOutput(
            input: List<Long>,
            inputValue: suspend () -> Long,
            outputValue: suspend (Long) -> Unit
        ) = IntComputer(input, outputValue, inputValue)

        fun parseWithSuspendInputOutput(
            input: InputRepresentation,
            inputValue: suspend () -> Long,
            outputValue: suspend (Long) -> Unit,
        ) = parseWithSuspendInputOutput(input.flatMap { it.toAllLongs() }, inputValue, outputValue)


        private fun Long.splitInInstructionAndItsParameters(
            memory: List<Long>,
            instructionPointer: Long,
            relativeBase: Long
        ): Pair<Instruction, List<Long>> {
            val opCode = this % 100
            val instruction = getInstruction(opCode)
            val digitsWithoutOpCodeFromRightToLeft = (this / 100).toString().reversed().asSequence()
            val parameterModes =
                digitsWithoutOpCodeFromRightToLeft.map { ParameterMode.entries[it.digitToInt()] } + generateSequence { ParameterMode.PositionMode }
            return instruction to parameterModes.withIndex()
                .map { (indexMinus1, parameterMode) ->
                    val content = memory[(indexMinus1 + 1 + instructionPointer).toInt()]
                    if (indexMinus1 + 1 in instruction.writesToParameters) {
                        parameterMode.transformWriteParameter(content, relativeBase)
                    } else {
                        parameterMode.transformReadParameter(content, relativeBase, memory)
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