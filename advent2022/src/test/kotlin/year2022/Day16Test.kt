package year2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day16Test {
    private val day = Day16()

    @Test
    fun part1() = assertEquals(1651, day.testPart1())


    @Test
    fun part2() = assertEquals(1707, day.testPart2())
}