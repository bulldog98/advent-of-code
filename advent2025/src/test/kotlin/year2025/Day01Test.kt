package year2025

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day01Test {
    private val day = Day01

    @Test
    fun part1() = assertEquals(3, day.testPart1())

    @Test
    fun part2() = assertEquals(6, day.testPart2())
}