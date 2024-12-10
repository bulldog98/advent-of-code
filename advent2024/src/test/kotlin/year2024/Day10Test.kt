package year2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day10Test {
    private val day = Day10
    @Test
    fun part1() = assertEquals(36, day.testPart1())

    @Test
    fun part2() = assertEquals(81, day.testPart2())
}