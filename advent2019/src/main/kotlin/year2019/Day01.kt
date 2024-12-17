package year2019

import adventday.AdventDay
import adventday.InputRepresentation

object Day01: AdventDay(2019, 1) {
    private fun Long.toFuelNeeded() = this / 3 - 2

    override fun part1(input: InputRepresentation): Long = input.sumOf {
        it.toLong().toFuelNeeded()
    }

    override fun part2(input: InputRepresentation): Long = input.sumOf {
        var computeFuelFor = it.toLong()
        var sum = 0L
        while (computeFuelFor.toFuelNeeded() > 0L) {
            val fuelNeeded = computeFuelFor.toFuelNeeded()
            computeFuelFor = fuelNeeded
            sum += fuelNeeded
        }
        sum
    }
}

fun main() = Day01.run()