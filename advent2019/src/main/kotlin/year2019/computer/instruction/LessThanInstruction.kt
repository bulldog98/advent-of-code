package year2019.computer.instruction

data object LessThanInstruction: Instruction {
    override val opCode: Long
        get() = 7
    override val numberOfParameters: Int
        get() = 3
    override val writesToParameters: List<Int>
        get() = listOf(3)

    override fun InstructionContext.executeWith(parameters: List<Long>) {
        assert(parameters.size == numberOfParameters) { "wrong number of parameters" }
        val (a, b, location) = parameters
        memory[location.toInt()] = if (a < b) 1 else 0
    }
}