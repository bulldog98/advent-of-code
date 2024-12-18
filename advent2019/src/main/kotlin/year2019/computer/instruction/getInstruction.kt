package year2019.computer.instruction

fun getInstruction(opCode: Long): Instruction = when (opCode) {
    1L -> AddInstruction
    2L -> MultiplicationInstruction
    3L -> InputInstruction
    4L -> OutputInstruction
    99L -> HaltInstruction
    else -> error("unknown instruction")
}