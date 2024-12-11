package year2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day11Test {
    private val day = Day11

    @Test
    fun splitWorks() = assertEquals(listOf(10L, 0L), (1000L).oneStep())

    @Test
    fun part1() = assertEquals(55312, day.testPart1())
}