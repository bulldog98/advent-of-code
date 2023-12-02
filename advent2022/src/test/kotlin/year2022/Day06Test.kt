package year2022

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day06Test {
    private val day = Day06()

    @Test
    fun part1() = assertEquals(7, day.testPart1())


    @Test
    fun part2() = assertEquals(19, day.testPart2())
}