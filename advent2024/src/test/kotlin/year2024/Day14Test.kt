package year2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day14Test {
    private val day = Day14(11, 7)

    @Test
    fun part1() = assertEquals(12L, day.testPart1())
}