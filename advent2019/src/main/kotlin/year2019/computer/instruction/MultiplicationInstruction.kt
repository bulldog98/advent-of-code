package year2019.computer.instruction

data object MultiplicationInstruction : Instruction {
    override val opCode: Long
        get() = 2
    override val numberOfParameters: Int
        get() = 3
    override val writesToParameters: List<Int>
        get() = listOf(3)

    override fun invoke(memoryContent: MutableList<Long>, paramaters: List<Long>) {
        assert(paramaters.size == AddInstruction.numberOfParameters) { "wrong number of parameters" }
        val (firstParameter, secondParameter, destinationAddress) = paramaters
        memoryContent[destinationAddress.toInt()] = firstParameter * secondParameter
    }
}
