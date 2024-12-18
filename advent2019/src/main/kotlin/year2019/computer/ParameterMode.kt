package year2019.computer

enum class ParameterMode(
    val transformReadParameter: (Long, List<Long>) -> Long,
    val transformWriteParameter: (Long) -> Long = { _ -> error("writing only supported by PositionMode") }
) {
    PositionMode(
        { it, memory -> memory[it.toInt()] },
        { it }
    ),
    ImmediateMode({ it, _ -> it })
}