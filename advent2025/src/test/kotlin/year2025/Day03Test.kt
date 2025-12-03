package year2025

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day03Test {
    private val day = Day03

    @Test
    fun part1() = assertEquals(357L, day.testPart1())

    @Test
    fun part2() = assertEquals(3121910778619L, day.testPart2())
}
