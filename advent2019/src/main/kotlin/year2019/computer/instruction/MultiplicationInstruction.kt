package year2019.computer.instruction

data object MultiplicationInstruction : Instruction {
    override val opCode: Long
        get() = 2
    override val numberOfParameters: Int
        get() = 3
    override val writesToParameters: List<Int>
        get() = listOf(3)

    override suspend fun InstructionContext.executeWith(parameters: List<Long>) {
        assert(parameters.size == AddInstruction.numberOfParameters) { "wrong number of parameters" }
        val (firstParameter, secondParameter, destinationAddress) = parameters
        memory[destinationAddress.toInt()] = firstParameter * secondParameter
    }
}
