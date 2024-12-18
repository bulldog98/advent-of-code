package year2019.computer.instruction

data object JumpIfFalseInstruction: Instruction {
    override val opCode: Long
        get() = 6
    override val numberOfParameters: Int
        get() = 2

    override suspend fun InstructionContext.executeWith(parameters: List<Long>) {
        assert(parameters.size == numberOfParameters) { "wrong number of parameters" }
        val (testValue, jumpToLocation) = parameters
        if (testValue == 0L) {
            this.jumpTo(jumpToLocation)
        }
    }
}