package year2025.day01

data class Dial(val position: Int = 50, val clicks: Int = 0)

operator fun Dial.plus(instruction: Instruction): Dial {
    val restWay = instruction.amount % 100
    val realPosition = when {
        position == 0 && instruction is Instruction.Left -> 100
        else -> position
    }
    val lastPos = realPosition + restWay * instruction.direction
    val clickOnLastRotation: Int = when {
        lastPos <= 0 -> 1
        lastPos >= 100 -> 1
        else -> 0
    }
    return copy(
        position = (position + (instruction.amount % 100) * instruction.direction).mod(100),
        clicks = clicks + instruction.amount / 100 + clickOnLastRotation
    )
}