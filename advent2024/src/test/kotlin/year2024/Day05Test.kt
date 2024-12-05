package year2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day05Test {
    private val day = Day05

    @Test
    fun part1() = assertEquals(143L, day.testPart1())

    @Test
    fun part2() = assertEquals(123L, day.testPart2())
}