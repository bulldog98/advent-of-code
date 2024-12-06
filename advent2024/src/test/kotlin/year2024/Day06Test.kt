package year2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day06Test {
    private val day = Day06

    @Test
    fun part1() = assertEquals(41, day.testPart1())

    @Test
    fun part2() = assertEquals(6, day.testPart2())
}