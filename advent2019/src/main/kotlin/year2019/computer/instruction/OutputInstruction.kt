package year2019.computer.instruction

data object OutputInstruction: Instruction {
    override val opCode: Long
        get() = 4
    override val numberOfParameters: Int
        get() = 1

    override fun invoke(context: InstructionContext, parameters: List<Long>) = with (context) {
        assert(parameters.size == InputInstruction.numberOfParameters) { "wrong number of parameters" }
        output(parameters[0])
    }
}
