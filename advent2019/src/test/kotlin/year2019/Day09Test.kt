package year2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day09Test {
    private val day = Day09

    // example with long number as output
    @Test
    fun part1() = assertEquals(1219070632396864L, day.testPart1())
}