package year2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day06Test {
    private val day = Day06

    @Test
    fun part1() = assertEquals(42L, day.testPart1("test1"))

    @Test
    fun part2() = assertEquals(4L, day.testPart2("test2"))
}