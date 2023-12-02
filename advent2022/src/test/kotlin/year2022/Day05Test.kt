package year2022

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day05Test {
    private val day = Day05()

    @Test
    fun part1() = assertEquals("CMZ", day.testPart1())


    @Test
    fun part2() = assertEquals("MCD", day.testPart2())
}