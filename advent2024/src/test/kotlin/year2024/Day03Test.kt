package year2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day03Test {
    private val day = Day03

    @Test
    fun part1() = assertEquals(161L, day.testPart1())

    @Test
    fun part2() = assertEquals(48L, day.testPart2())
}