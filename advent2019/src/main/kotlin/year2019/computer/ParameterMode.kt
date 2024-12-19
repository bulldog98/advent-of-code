package year2019.computer

enum class ParameterMode(
    val transformReadParameter: (Long, Long, List<Long>) -> Long,
    val transformWriteParameter: (Long, Long) -> Long = { _, _ -> error("writing only supported by PositionMode or RelativeMode") }
) {
    PositionMode(
        { it, _, memory -> memory[it.toInt()] },
        { it, _ -> it }
    ),
    ImmediateMode({ it, _, _ -> it }),
    RelativeMode(
        { it, relativeBase, memory -> memory[(it + relativeBase).toInt()] },
        { it, relativeOffset -> it + relativeOffset }),
}