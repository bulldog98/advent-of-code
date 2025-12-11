package year2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day15Test {
    val day = Day15

    @Test
    fun part1() = assertEquals(62842880, day.testPart1())

    @Test
    fun part2() = assertEquals(57600000, day.testPart2())
}
