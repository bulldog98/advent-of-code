package year2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day01Test {
    private val day = Day01()

    @Test
    fun part1() = assertEquals(24000, day.testPart1())


    @Test
    fun part2() = assertEquals(45000, day.testPart2())
}