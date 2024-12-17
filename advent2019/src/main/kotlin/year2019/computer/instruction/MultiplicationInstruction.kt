package year2019.computer.instruction

data object MultiplicationInstruction : Instruction {
    override val opCode: Long
        get() = 2
    override val numberOfParameters: Int
        get() = 3

    override fun invoke(memoryContent: MutableList<Long>, paramaters: List<Long>) {
        assert(paramaters.size == AddInstruction.numberOfParameters) { "wrong number of parameters" }
        val (firstParameterAddress, secondParameterAddress, destinationAddress) = paramaters.map { it.toInt() }
        // val destinationAddress = memoryContent[destinationAddressAddress].toInt()
        memoryContent[destinationAddress] = memoryContent[firstParameterAddress] * memoryContent[secondParameterAddress]
    }
}
