package year2025

import adventday.AdventDay
import adventday.InputRepresentation

sealed interface Instruction : (Long) -> Long {
    data class Left(override val amount: Long) : Instruction {
        override fun invoke(a: Long): Long = a - amount
    }

    data class Right(override val amount: Long) : Instruction {
        override fun invoke(a: Long): Long = a + amount
    }

    val amount: Long

    companion object {
        fun parse(inputLine: String): Instruction = when {
            inputLine.startsWith("L") -> Left(inputLine.drop(1).toLong())
            inputLine.startsWith("R") -> Right(inputLine.drop(1).toLong())
            else -> error("could not parse")
        }
    }
}

data class Dial(val position: Long, val clicks: Long = 0) {
    fun execute(instruction: Instruction): Dial {
        var newClicks = 0L
        var nextPosition = position
        val direction = if (instruction is Instruction.Left) -1 else 1
        repeat(instruction.amount.toInt()) {
            nextPosition = (nextPosition + direction + 100L) % 100
            if (nextPosition % 100L == 0L) {
                newClicks++
            }
        }
        return copy(
            position = nextPosition,
            clicks = clicks + newClicks
        )
    }
}

object Day01 : AdventDay(2025, 1) {
    override fun part1(input: InputRepresentation): Long = input
        .map(Instruction::parse)
        .runningFold(50L) { acc, instruction ->
            instruction(acc)
        }
        .count { it % 100L == 0L }.toLong()

    override fun part2(input: InputRepresentation): Long = input
        .map(Instruction::parse)
        .fold(Dial(50)) { dial, instruction ->
            dial.execute(instruction)
        }.clicks
}

fun main() = Day01.run()
