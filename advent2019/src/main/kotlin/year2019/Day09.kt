package year2019

import adventday.AdventDay
import adventday.InputRepresentation
import year2019.computer.IntComputer

object Day09: AdventDay(2019, 9) {
    override fun part1(input: InputRepresentation): Long {
        val output = mutableListOf<Long>()
        val computer = IntComputer.parse(input, { output += it}) { 1 }
        computer.simulateUntilHalt()
        return output.single()
    }

    override fun part2(input: InputRepresentation): Long {
        TODO("Not yet implemented")
    }
}

fun main() = Day09.run()