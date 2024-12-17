package year2019.computer.instruction

data object HaltInstruction : Instruction {
    override val opCode: Long
        get() = 99
    override val numberOfParameters: Int
        get() = 0

    override fun invoke(memoryContent: MutableList<Long>, parameters: List<Long>) {
        // noop do nothing just halt
    }
}