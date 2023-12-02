package year2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day04Test {
    private val day = Day04()

    @Test
    fun part1() = assertEquals(2, day.testPart1())


    @Test
    fun part2() = assertEquals(4, day.testPart2())
}