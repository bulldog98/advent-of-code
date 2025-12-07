package year2025.day01

sealed interface Instruction {
    data class Left(override val amount: Int) : Instruction {
        override val direction: Int
            get() = -1
    }

    data class Right(override val amount: Int) : Instruction {
        override val direction: Int
            get() = 1
    }

    val amount: Int
    val direction: Int

    companion object {
        fun parse(inputLine: String): Instruction = when {
            inputLine.startsWith("L") -> Left(inputLine.drop(1).toInt())
            inputLine.startsWith("R") -> Right(inputLine.drop(1).toInt())
            else -> error("could not parse")
        }
    }
}