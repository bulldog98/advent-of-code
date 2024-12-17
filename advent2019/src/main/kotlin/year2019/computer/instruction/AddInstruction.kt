package year2019.computer.instruction

data object AddInstruction: Instruction {
    override val opCode: Long
        get() = 1
    override val numberOfParameters: Int
        get() = 3

    override fun invoke(memoryContent: MutableList<Long>, paramaters: List<Long>) {
        assert(paramaters.size == numberOfParameters) { "wrong number of parameters" }
        val (firstParameterAddress, secondParameterAddress, destinationAddress) = paramaters.map { it.toInt() }
        memoryContent[destinationAddress] = memoryContent[firstParameterAddress] + memoryContent[secondParameterAddress]
    }
}