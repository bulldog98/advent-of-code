package year2022

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day09Test {
    private val day = Day09()

    @Test
    fun part1() = assertEquals(13, day.testPart1())


    @Test
    fun part2() = assertEquals(36, day.testPart2())
}