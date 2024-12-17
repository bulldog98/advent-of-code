package year2024.day17

sealed interface Instruction: (ComputerState, Long) -> ComputerState {
    fun ComputerState.computeDvValue(operand: Long) = registryState.registerA / powerOf2(
        ComboOperand.fromOperand(operand)(registryState)
    )

    private data object AdvInstruction : Instruction {
        override fun invoke(
            computerState: ComputerState,
            operand: Long
        ): ComputerState = computerState.computeNextState {
            registerA = computerState.computeDvValue(operand)
        }
    }
    private data object BlxInstruction: Instruction {
        override fun invoke(
            computerState: ComputerState,
            operand: Long
        ): ComputerState = computerState.computeNextState {
            registerB = registerB xor operand
        }
    }
    private data object BstInstruction: Instruction {
        override fun invoke(
            computerState: ComputerState,
            operand: Long
        ): ComputerState = computerState.computeNextState {
            registerB = ComboOperand.fromOperand(operand)(computerState.registryState)
        }
    }
    private data object JnzInstruction: Instruction {
        override fun invoke(
            computerState: ComputerState,
            operand: Long
        ): ComputerState = computerState.computeNextState {
            if (registerA != 0L) {
                nextInstructionPointer = operand.toInt()
            }
        }
    }
    private data object BxcInstruction: Instruction {
        override fun invoke(
            computerState: ComputerState,
            operand: Long
        ): ComputerState = computerState.computeNextState {
            registerB = registerB xor registerC
        }
    }
    private data object OutInstruction: Instruction {
        override fun invoke(
            computerState: ComputerState,
            operand: Long
        ): ComputerState = computerState.computeNextState {
            output(ComboOperand.fromOperand(operand)(computerState.registryState) % 8)
        }
    }
    private data object BdvInstruction: Instruction {
        override fun invoke(
            computerState: ComputerState,
            operand: Long
        ): ComputerState = computerState.computeNextState {
            registerB = computerState.computeDvValue(operand)
        }
    }
    private data object CdvInstruction: Instruction {
        override fun invoke(
            computerState: ComputerState,
            operand: Long
        ): ComputerState = computerState.computeNextState {
            registerC = computerState.computeDvValue(operand)
        }
    }

    companion object {
        private class ComputerStateBuilder(private val oldState: ComputerState) {
            private val registerChanges = mutableListOf<(RegistryState) -> RegistryState>()
            var registerA: Long
                get() = oldState.registryState.registerA
                set(value) {
                    registerChanges += { it.copy(registerA = value) }
                }
            var registerB: Long
                get() = oldState.registryState.registerB
                set(value) {
                    registerChanges += { it.copy(registerB = value) }
                }
            var registerC: Long
                get() = oldState.registryState.registerC
                set(value) {
                    registerChanges += { it.copy(registerC = value) }
                }
            var nextInstructionPointer = oldState.instructionPointer + 2
            var addedOutputs = mutableListOf<Long>()

            fun output(value: Long) {
                addedOutputs += value
            }

            fun applyToRegistryState(oldRegistryState: RegistryState) =
                registerChanges.fold(oldRegistryState) { registryState, modification ->
                    modification(registryState)
                }
        }

        private fun ComputerState.computeNextState(instructions: ComputerStateBuilder.() -> Unit): ComputerState =
            ComputerStateBuilder(this).apply {
                instructions()
            }.run {
                copy(
                    registryState = applyToRegistryState(registryState),
                    instructionPointer = nextInstructionPointer,
                    output = output + addedOutputs
                )
            }

        fun fromOpCode(opCode: Long): (ComputerState, Long) -> ComputerState = when (opCode) {
            0L -> AdvInstruction
            1L -> BlxInstruction
            2L -> BstInstruction
            3L -> JnzInstruction
            4L -> BxcInstruction
            5L -> OutInstruction
            6L -> BdvInstruction
            7L -> CdvInstruction
            else -> error("invalid instruction only 0-7 allowed")
        }
    }
}