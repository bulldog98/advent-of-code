package year2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day17Test {
    val day = Day17(25)

    @Test
    fun part1() = assertEquals(4, day.testPart1())

    @Test
    fun part2() = assertEquals(3, day.testPart2())
}
