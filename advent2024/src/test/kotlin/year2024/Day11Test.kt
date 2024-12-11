package year2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day11Test {
    private val day = Day11

    @Test
    fun splitWorks() = assertEquals(listOf(10L, 0L), (1000L).oneStep())

    @Test
    fun part1() = assertEquals(55312L, day.testPart1())

    @Test
    fun part2() = assertEquals(65601038650482L, day.testPart2())
}