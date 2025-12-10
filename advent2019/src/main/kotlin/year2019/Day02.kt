package year2019

import adventday.AdventDay
import adventday.InputRepresentation
import kotlinx.coroutines.runBlocking
import year2019.computer.IntComputer

object Day02: AdventDay(2019, 2, "1202 Program Alarm") {
    override fun part1(input: InputRepresentation): Long = runBlocking {
        val computer = IntComputer.parse(input).also {
            it[1] = 12
            it[2] = 2
        }

        computer.simulateUntilHalt()[0]
    }

    override fun part2(input: InputRepresentation): Long = (0..99L).flatMap { noun ->
        (0..99L).filter { verb ->
            val computer = IntComputer.parse(input)
            computer[1] = noun
            computer[2] = verb
            runBlocking {
                computer.simulateUntilHalt()[0] == 19690720L
            }
        }.map { verb ->
            100 * noun + verb
        }
    }.firstOrNull() ?: error("no solution found")
}

fun main() = Day02.run()