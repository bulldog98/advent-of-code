package year2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day08Test {
    private val day = Day08()

    @Test
    fun part1() = assertEquals(21, day.testPart1())


    @Test
    fun part2() = assertEquals(8, day.testPart2())
}