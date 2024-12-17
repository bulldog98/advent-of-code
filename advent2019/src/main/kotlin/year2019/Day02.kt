package year2019

import adventday.AdventDay
import adventday.InputRepresentation
import year2019.computer.IntComputer

object Day02: AdventDay(2019, 2) {
    override fun part1(input: InputRepresentation): Long {
        val computer = IntComputer.parse(input).also {
            it[1] = 12
            it[2] = 2
        }

        return computer.simulateUntilHalt()[0]
    }

    override fun part2(input: InputRepresentation): Long {
        val program = IntComputer.parse(input)
        val a = mutableListOf(1)
        a[1] = 2

        TODO("Not yet implemented")
    }
}

fun main() = Day02.run()