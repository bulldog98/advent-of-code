package year2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day13Test {
    private val day = Day13()

    @Test
    fun part1() = assertEquals(13, day.testPart1())


    @Test
    fun part2() = assertEquals(140, day.testPart2())
}