package year2019.computer.instruction

data object JumpIfTrueInstruction: Instruction {
    override val opCode: Long
        get() = 5
    override val numberOfParameters: Int
        get() = 2

    override fun InstructionContext.executeWith(parameters: List<Long>) {
        assert(parameters.size == numberOfParameters) { "wrong number of parameters" }
        val (testValue, jumpToLocation) = parameters
        if (testValue > 0) {
            this.jumpTo(jumpToLocation)
        }
    }
}