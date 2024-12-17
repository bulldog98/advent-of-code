package year2024

import adventday.AdventDay
import adventday.InputRepresentation
import helper.numbers.toAllLongs

object Day17 : AdventDay(2024, 17) {
    private data class RegistryState(
        val registerA: Int,
        val registerB: Int,
        val registerC: Int,
    ) {
        companion object {
            fun parse(input: List<String>): RegistryState {
                assert(input.size == 3)
                return RegistryState(
                    input[0].toAllLongs().first().toInt(),
                    input[1].toAllLongs().first().toInt(),
                    input[2].toAllLongs().first().toInt(),
                )
            }
        }
    }

    private sealed interface ComboOperand {
        fun getValue(data: RegistryState): Int

        data object ComboOperand0: ComboOperand {
            override fun getValue(data: RegistryState): Int = 0
        }
        data object ComboOperand1: ComboOperand {
            override fun getValue(data: RegistryState): Int = 1
        }
        data object ComboOperand2: ComboOperand {
            override fun getValue(data: RegistryState): Int = 2
        }
        data object ComboOperand3: ComboOperand {
            override fun getValue(data: RegistryState): Int = 3
        }
        data object ComboOperand4: ComboOperand {
            override fun getValue(data: RegistryState): Int = data.registerA
        }
        data object ComboOperand5: ComboOperand {
            override fun getValue(data: RegistryState): Int = data.registerB
        }
        data object ComboOperand6: ComboOperand {
            override fun getValue(data: RegistryState): Int = data.registerC
        }
        data object ComboOperand7: ComboOperand {
            override fun getValue(data: RegistryState): Int = error("reserved op code")
        }

        companion object {
            fun fromOperand(opCode: Int) = when (opCode) {
                0 -> ComboOperand0
                1 -> ComboOperand1
                2 -> ComboOperand2
                3 -> ComboOperand3
                4 -> ComboOperand4
                5 -> ComboOperand5
                6 -> ComboOperand6
                else -> ComboOperand7
            }
        }
    }

    private data class ComputerState(
        val registryState: RegistryState,
        val instructions: List<Int>,
        val instructionPointer: Int = 0
    )

    fun powerOf2(num: Int) = if (num == 0)
        1
    else
        (2).shl(num - 1)

    override fun part1(input: InputRepresentation): String {
        val (initialState, instructionText) = input.asTwoBlocks()
        val initialRegistryState = RegistryState.parse(initialState)
        val instructions = instructionText[0].toAllLongs().toList().map { it.toInt() }
        val output = buildList<Int> {
            var computerState = ComputerState(
                initialRegistryState,
                instructions
            )
            while (instructions.getOrNull(computerState.instructionPointer) != null && instructions.getOrNull(computerState.instructionPointer + 1) != null) {
                val opCode = instructions[computerState.instructionPointer]
                val operand = instructions[computerState.instructionPointer + 1]
                when (opCode) {
                    // adv
                    0 -> {
                        val newA = computerState.registryState.registerA / powerOf2(ComboOperand.fromOperand(operand).getValue(computerState.registryState))
                        computerState = computerState.copy(
                            registryState = computerState.registryState.copy(
                                registerA = newA
                            ),
                            instructionPointer = computerState.instructionPointer + 2
                        )
                    }
                    // blx
                    1 -> {
                        val newB = computerState.registryState.registerB xor operand
                        computerState = computerState.copy(
                            registryState = computerState.registryState.copy(
                                registerB = newB
                            ),
                            instructionPointer = computerState.instructionPointer + 2
                        )
                    }
                    // bst
                    2 -> {
                        val comboOperant = ComboOperand.fromOperand(operand)
                        val newB = comboOperant.getValue(computerState.registryState) % 8
                        computerState = computerState.copy(
                            registryState = computerState.registryState.copy(
                                registerB = newB
                            ),
                            instructionPointer = computerState.instructionPointer + 2
                        )
                    }
                    // jnz
                    3 -> {
                        computerState = if (computerState.registryState.registerA == 0) {
                             computerState.copy(
                                instructionPointer = computerState.instructionPointer + 2
                            )
                        } else {
                            computerState.copy(
                                instructionPointer = operand
                            )
                        }
                    }
                    // bxc
                    4 -> {
                        val newB = computerState.registryState.registerB xor computerState.registryState.registerC
                        computerState = computerState.copy(
                            registryState = computerState.registryState.copy(
                                registerB = newB
                            ),
                            instructionPointer = computerState.instructionPointer + 2
                        )
                    }
                    // out
                    5 -> {
                        val comboOperand = ComboOperand.fromOperand(operand)
                        add(comboOperand.getValue(computerState.registryState) % 8)
                        computerState = computerState.copy(
                            instructionPointer = computerState.instructionPointer + 2
                        )
                    }
                    // bdv
                    6 -> {
                        val newB = computerState.registryState.registerA / powerOf2(ComboOperand.fromOperand(operand).getValue(computerState.registryState))
                        computerState = computerState.copy(
                            registryState = computerState.registryState.copy(
                                registerB = newB
                            ),
                            instructionPointer = computerState.instructionPointer + 2
                        )
                    }
                    // cdv
                    7 -> {
                        val newC = computerState.registryState.registerA / powerOf2(ComboOperand.fromOperand(operand).getValue(computerState.registryState))
                        computerState = computerState.copy(
                            registryState = computerState.registryState.copy(
                                registerC = newC
                            ),
                            instructionPointer = computerState.instructionPointer + 2
                        )
                    }
                }
            }
        }
        return output.joinToString(",") { it.toString() }
    }

    override fun part2(input: InputRepresentation): Int {
        TODO("Not yet implemented")
    }
}

fun main() = Day17.run()