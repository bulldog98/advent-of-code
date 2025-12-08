package year2025

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day08Test {
    private val day = Day08(10)

    @Test
    fun part1() = assertEquals(40L, day.testPart1())

    @Test
    fun part2() = assertEquals(25272L, day.testPart2())
}
