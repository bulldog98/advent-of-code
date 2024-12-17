package year2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day01Test {
    private val day = Day01

    // sum of the examples
    @Test
    fun part1() = assertEquals(34241L, day.testPart1())
}