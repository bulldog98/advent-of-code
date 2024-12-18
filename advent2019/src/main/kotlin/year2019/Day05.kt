package year2019

import adventday.AdventDay
import adventday.InputRepresentation
import year2019.computer.IntComputer

object Day05: AdventDay(2019, 5) {
    override fun part1(input: InputRepresentation): Long {
        val output = mutableListOf<Long>()
        val computer = IntComputer.parse(input, output::add) { 1 }
        computer.simulateUntilHalt()
        return output.single { it != 0L }
    }

    override fun part2(input: InputRepresentation): Long {
        val output = mutableListOf<Long>()
        val computer = IntComputer.parse(input, output::add) { 5 }
        computer.simulateUntilHalt()
        return output.single { it != 0L }
    }
}

fun main() = Day05.run()