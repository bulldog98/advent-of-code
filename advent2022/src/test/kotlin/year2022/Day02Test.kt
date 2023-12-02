package year2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day02Test {
    private val day = Day02()

    @Test
    fun part1() = assertEquals(15, day.testPart1())


    @Test
    fun part2() = assertEquals(12, day.testPart2())
}