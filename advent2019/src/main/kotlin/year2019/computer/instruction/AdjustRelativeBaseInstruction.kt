package year2019.computer.instruction

data object AdjustRelativeBaseInstruction: Instruction {
    override val opCode: Long
        get() = 9
    override val numberOfParameters: Int
        get() = 1

    override suspend fun InstructionContext.executeWith(parameters: List<Long>) {
        assert(parameters.size == numberOfParameters) { "wrong number of parameters" }
        adjustRelativeBase(parameters.single())
    }
}
