package year2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day08Test {
    val day = Day08

    // all examples in one file are the test file

    @Test
    fun part1() = assertEquals(12L, day.testPart1())

    @Test
    fun part2() = assertEquals(19L, day.testPart2())
}
