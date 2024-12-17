package year2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class Day17Test {
    private val day = Day17

    @Test
    fun powerOf2Works() = assertAll(
        { assertEquals(1, day.powerOf2(0)) },
        { assertEquals(2, day.powerOf2(1)) },
        { assertEquals(4, day.powerOf2(2)) },
    )

    @Test
    fun part1() = assertEquals("4,6,3,5,6,3,5,2,1,0", day.testPart1())
}