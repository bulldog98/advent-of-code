package year2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day13Test {
    private val day = Day13

    @Test
    fun part1() = assertEquals(480L, day.testPart1())

    @Test
    fun part2() = assertEquals((875318608908L), day.testPart2())
}