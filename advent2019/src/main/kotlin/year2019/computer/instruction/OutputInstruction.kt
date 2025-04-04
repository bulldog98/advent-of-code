package year2019.computer.instruction

data object OutputInstruction: Instruction {
    override val opCode: Long
        get() = 4
    override val numberOfParameters: Int
        get() = 1

    override suspend fun InstructionContext.executeWith(parameters: List<Long>) {
        assert(parameters.size == InputInstruction.numberOfParameters) { "wrong number of parameters" }
        this.output(parameters[0])
    }
}
