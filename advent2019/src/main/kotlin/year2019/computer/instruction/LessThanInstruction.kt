package year2019.computer.instruction

data object LessThanInstruction: Instruction {
    override val opCode: Long
        get() = 7
    override val numberOfParameters: Int
        get() = 3
    override val writesToParameters: List<Int>
        get() = listOf(3)

    override fun invoke(context: InstructionContext, parameters: List<Long>) = with(context) {
        assert(parameters.size == numberOfParameters) { "wrong number of parameters" }
        val (a, b, location) = parameters
        memory[location.toInt()] = if (a < b) 1 else 0
    }
}