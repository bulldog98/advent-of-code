package year2015.day06

sealed interface Instruction : (Int) -> Int {
    data object TurnOnPart1 : Instruction {
        override fun invoke(unused: Int) = 1
    }
    data object TurnOffPart1 : Instruction {
        override fun invoke(unused: Int) = 0
    }
    data object TogglePart1 : Instruction {
        override fun invoke(value: Int) = if (value == 0) 1 else 0
    }
    data object TurnOnPart2 : Instruction {
        override fun invoke(brightness: Int) = brightness + 1
    }
    data object TurnOffPart2 : Instruction {
        override fun invoke(brightness: Int) = (brightness - 1).coerceAtLeast(0)
    }
    data object TogglePart2 : Instruction {
        override fun invoke(brightness: Int) = brightness + 2
    }
    companion object {
        fun parsePart1(instruction: String) = when (instruction.trim()) {
            "turn on" -> TurnOnPart1
            "turn off" -> TurnOffPart1
            "toggle" -> TogglePart1
            else -> error("Unknown instruction: $instruction")
        }
        fun parsePart2(instruction: String) = when (instruction.trim()) {
            "turn on" -> TurnOnPart2
            "turn off" -> TurnOffPart2
            "toggle" -> TogglePart2
            else -> error("Unknown instruction: $instruction")
        }
    }
}