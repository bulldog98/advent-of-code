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
        val nextInstruction = Instruction.fromOpCode(opCode)
        computerState = nextInstruction(computerState, operand)
    }
//        println("$initialComputerState took $steps steps and produced $this")
    addAll(computerState.output)
}