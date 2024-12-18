package year2019.computer.instruction

data object EqualsInstruction: Instruction {
    override val opCode: Long
        get() = 8
    override val numberOfParameters: Int
        get() = 3
    override val writesToParameters: List<Int>
        get() = listOf(3)

    override suspend fun InstructionContext.executeWith(parameters: List<Long>) {
        assert(parameters.size == numberOfParameters) { "wrong number of parameters" }
        val (a, b, location) = parameters
        memory[location.toInt()] = if (a == b) 1 else 0
    }
}