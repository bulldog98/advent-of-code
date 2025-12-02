package year2025

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day02Test {
    private val day = Day02

    @Test
    fun part1() = assertEquals(1227775554L, day.testPart1())

    @Test
    fun part2() = assertEquals(4174379265L, day.testPart2())
}
