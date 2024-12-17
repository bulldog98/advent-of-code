package year2024.day17

fun powerOf2(num: Long) = when {
    num < 0L -> error("negative number")
    num == 0L -> 1L
    num <= Int.MAX_VALUE.toLong() -> (2L).shl(num.toInt() - 1)
    else -> error("try higher than Int.MAX_VALUE")
}

fun simulateProgram(initialComputerState: ComputerState) = buildList {
    var computerState = initialComputerState
    var steps = 0
    while (computerState.canContinue()) {
        steps++
        val (opCode, operand) = computerState.getInstructions()
        // println("opCode: $opCode, operand: $operand, regs: ${computerState.registryState.registerA.toString(8)}, ${computerState.registryState.registerB.toString(8)}, ${computerState.registryState.registerC.toString(8)}")
        when (opCode.toInt()) {
            // adv
            0 -> {
                val newA = computerState.registryState.registerA / powerOf2(
                    ComboOperand.fromOperand(operand)(computerState.registryState)
                )
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
                val newB = comboOperant(computerState.registryState) % 8
                computerState = computerState.copy(
                    registryState = computerState.registryState.copy(
                        registerB = newB
                    ),
                    instructionPointer = computerState.instructionPointer + 2
                )
            }
            // jnz
            3 -> {
                computerState = if (computerState.registryState.registerA == 0L) {
                    computerState.copy(
                        instructionPointer = computerState.instructionPointer + 2
                    )
                } else {
                    computerState.copy(
                        instructionPointer = operand.toInt()
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
                add(comboOperand(computerState.registryState) % 8)
                computerState = computerState.copy(
                    instructionPointer = computerState.instructionPointer + 2
                )
            }
            // bdv
            6 -> {
                val newB = computerState.registryState.registerA / powerOf2(
                    ComboOperand.fromOperand(operand)(computerState.registryState)
                )
                computerState = computerState.copy(
                    registryState = computerState.registryState.copy(
                        registerB = newB
                    ),
                    instructionPointer = computerState.instructionPointer + 2
                )
            }
            // cdv
            7 -> {
                val newC = computerState.registryState.registerA / powerOf2(
                    ComboOperand.fromOperand(operand)(computerState.registryState)
                )
                computerState = computerState.copy(
                    registryState = computerState.registryState.copy(
                        registerC = newC
                    ),
                    instructionPointer = computerState.instructionPointer + 2
                )
            }
        }
    }
//        println("$initialComputerState took $steps steps and produced $this")
}