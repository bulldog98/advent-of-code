package year2019.computer.instruction

/**
 * this is a function that has the memory as mutable list in this and the parameter values in the parameter list,
 * it's expected that you throw if the list size does not match numberOfParameters
 */
sealed interface Instruction {
    val opCode: Long
    val numberOfParameters: Int
    // numbers in here are counted by 1 for parameter 1 and so on
    val writesToParameters: List<Int>
        get() = emptyList()

    fun InstructionContext.executeWith(parameters: List<Long>)
}

