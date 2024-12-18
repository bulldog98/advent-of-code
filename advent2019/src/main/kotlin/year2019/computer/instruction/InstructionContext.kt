package year2019.computer.instruction

class InstructionContext(
    val memory: MutableList<Long>,
    val jumpTo: (Long) -> Unit
)