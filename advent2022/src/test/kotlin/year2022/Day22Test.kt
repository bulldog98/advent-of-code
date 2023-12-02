package year2022

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class Day22Test {
    private val day = Day22()

    @Test
    fun part1() = assertEquals(6032L, day.testPart1())


    @Disabled("failing and not implemented")
    @Test
    fun part2() = assertEquals(5031L, day.testPart2())
}