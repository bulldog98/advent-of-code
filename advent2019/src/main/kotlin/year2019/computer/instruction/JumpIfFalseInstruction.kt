package year2019.computer.instruction

data object JumpIfFalseInstruction: Instruction {
    override val opCode: Long
        get() = 6
    override val numberOfParameters: Int
        get() = 2

    override fun invoke(context: InstructionContext, parameters: List<Long>) = with(context) {
        assert(parameters.size == numberOfParameters) { "wrong number of parameters" }
        val (testValue, jumpToLocation) = parameters
        if (testValue == 0L) {
            jumpTo(jumpToLocation)
        }
    }
}