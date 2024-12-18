package year2019.computer

enum class ParameterMode(val transformParameter: (Long, List<Long>) -> Long) {
    PositionMode({ it, memory -> memory[it.toInt()] }),
    ImmediateMode({ it, _ -> it })
}