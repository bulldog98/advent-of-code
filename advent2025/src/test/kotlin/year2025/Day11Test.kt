package year2025

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day11Test {
    private val day = Day11

    @Test
    fun part1() = assertEquals(5L, day.testPart1())


    // input is the adjusted example for part 2
    @Test
    fun part2() = assertEquals(2L, day.testPart2("example2"))
}
