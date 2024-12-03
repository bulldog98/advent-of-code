package year2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day01Test {
    private val day = Day01

    @Test
    fun part1() = assertEquals(11L, day.testPart1())
    @Test
    fun part2() = assertEquals(31L, day.testPart2())
}