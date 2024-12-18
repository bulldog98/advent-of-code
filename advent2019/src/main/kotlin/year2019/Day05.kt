package year2019

import adventday.AdventDay
import adventday.InputRepresentation
import year2019.computer.IntComputer

object Day05: AdventDay(2019, 5) {
    override fun part1(input: InputRepresentation): Long {
        val computer = IntComputer.parse(input)

        return computer.simulateUntilHalt()[0]
    }

    override fun part2(input: InputRepresentation): Long {
        TODO("Not yet implemented")
    }
}

fun main() = Day05.run()