package year2025

import adventday.AdventDay
import adventday.InputRepresentation

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
        position = (position + (instruction.amount % 100) * instruction.direction + 100) % 100,
        clicks = clicks + instruction.amount / 100 + clickOnLastRotation
    )
}

object Day01 : AdventDay(2025, 1) {
    override fun part1(input: InputRepresentation): Int = input
        .map(Instruction::parse)
        .runningFold(Dial()) { acc, instruction ->
            acc + instruction
        }
        .count { it.position == 0 }

    override fun part2(input: InputRepresentation): Int = input
        .map(Instruction::parse)
        .fold(Dial()) { dial, instruction ->
            dial + instruction
        }.clicks
}

fun main() = Day01.run()
