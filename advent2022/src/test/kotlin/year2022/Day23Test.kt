package year2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day23Test {
    private val day = Day23()

    @Test
    fun part1() = assertEquals(110L, day.testPart1())


    @Test
    fun part2() = assertEquals(20, day.testPart2())
}