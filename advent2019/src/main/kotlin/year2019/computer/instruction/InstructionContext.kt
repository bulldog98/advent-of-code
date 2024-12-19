package year2019.computer.instruction

class InstructionContext(
    val memory: MutableList<Long>,
    val getInput: suspend () -> Long,
    val output: suspend (Long) -> Unit,
    val adjustRelativeBase: (Long) -> Unit,
    val jumpTo: (Long) -> Unit,
)