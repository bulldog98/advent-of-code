package year2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day20Test {
    private val dayForPart2 = Day20(50)
    private val dayForPart1 = Day20(1)

    @Test
    fun part1() = assertEquals(44, dayForPart1.testPart1())


    @Test
    fun part2() = assertEquals(285, dayForPart2.testPart2())
}