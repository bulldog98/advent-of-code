package year2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day07Test {
    private val day = Day07

    @Test
    fun part1() = assertEquals(43210L, day.testPart1())

    @Test
    fun part2() = assertEquals(139629729L, day.testPart2("part2_example1"))

    // simple test program that output input: 3,1,3,3,4,3,99
    @Test
    fun doesComplete() = assertEquals(0L, day.testPart2("part2_output_read"))
}