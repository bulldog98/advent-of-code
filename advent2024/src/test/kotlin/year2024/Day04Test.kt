package year2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day04Test {
    private val day = Day04

    @Test
    fun part1() = assertEquals(18, day.testPart1())

    @Test
    fun part2() = assertEquals(9, day.testPart2())
}