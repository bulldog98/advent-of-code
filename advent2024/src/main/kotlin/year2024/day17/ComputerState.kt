package year2024.day17

data class ComputerState(
    val registryState: RegistryState,
    val instructions: List<Long>,
    val instructionPointer: Int = 0,
    val output: List<Long> = emptyList(),
) {
    fun canContinue() = instructions.getOrNull(instructionPointer) != null &&
        instructions.getOrNull(instructionPointer + 1) != null

    fun getInstructions() = instructions[instructionPointer] to instructions[instructionPointer + 1]

    fun overrideRegisterA(newA: Long) = copy(
        registryState = registryState.copy(
            registerA = newA
        )
    )
}