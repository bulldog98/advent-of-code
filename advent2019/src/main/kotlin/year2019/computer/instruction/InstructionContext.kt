package year2019.computer.instruction

class InstructionContext(
    val memory: MutableList<Long>,
    val getInput: () -> Long,
    val output: (Long) -> Unit,
    val jumpTo: (Long) -> Unit,
)