package year2019.computer.instruction

data object InputInstruction: Instruction {
    override val opCode: Long
        get() = 3
    override val numberOfParameters: Int
        get() = 1
    override val writesToParameters: List<Int>
        get() = listOf(1)

    override fun invoke(memoryContent: MutableList<Long>, parameters: List<Long>) {
        assert(parameters.size == numberOfParameters) { "wrong number of parameters" }
        println("provide input")
        val input = readln()
        memoryContent[parameters[0].toInt()] = input.toLong()
    }
}