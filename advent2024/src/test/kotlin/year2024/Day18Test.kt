package year2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day18Test {
    private val day = Day18(6, 12)

    @Test
    fun part1() = assertEquals(22L, day.testPart1())

    @Test
    fun part2() = assertEquals("6,1", day.testPart2())
}