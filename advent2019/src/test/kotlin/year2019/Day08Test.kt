package year2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day08Test {
    private val day = Day08(3, 2)

    @Test
    fun part1() = assertEquals(1, day.testPart1())
}