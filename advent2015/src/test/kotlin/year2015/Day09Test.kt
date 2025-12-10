package year2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day09Test {
    val day = Day09

    @Test
    fun part1() = assertEquals(605, day.testPart1())

    @Test
    fun part2() = assertEquals(982, day.testPart2())
}
