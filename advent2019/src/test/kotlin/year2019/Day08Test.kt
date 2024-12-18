package year2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day08Test {
    private val dayForPart1 = Day08(3, 2)
    private val dayForPart2 = Day08(2, 2)

    @Test
    fun part1() = assertEquals(1, dayForPart1.testPart1())


    @Test
    fun part2() = assertEquals("01\n10", dayForPart2.testPart2("test2"))
}