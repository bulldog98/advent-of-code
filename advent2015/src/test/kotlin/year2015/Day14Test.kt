package year2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day14Test {
    val day = Day14(1000)

    @Test
    fun part1() = assertEquals(1120, day.testPart1())

    @Test
    fun part2() = assertEquals(689, day.testPart2())
}
