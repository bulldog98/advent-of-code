package year2019.computer.instruction

data object InputInstruction: Instruction {
    override val opCode: Long
        get() = 3
    override val numberOfParameters: Int
        get() = 1
    override val writesToParameters: List<Int>
        get() = listOf(1)

    override fun invoke(context: InstructionContext, parameters: List<Long>) = with(context) {
        assert(parameters.size == numberOfParameters) { "wrong number of parameters" }
        memory[parameters[0].toInt()] = getInput()
    }
}