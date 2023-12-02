package year2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day15Test {
    private val day = Day15(10, 20)

    @Test
    fun part1() = assertEquals(26, day.testPart1())


    @Test
    fun part2() = assertEquals(56000011L, day.testPart2())
}