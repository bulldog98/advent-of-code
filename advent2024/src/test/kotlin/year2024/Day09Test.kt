package year2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day09Test {
    private val day = Day09

    @Test
    fun part1() = assertEquals(1928L, day.testPart1())

    @Test
    fun part2() = assertEquals(2858L, day.testPart2())
}