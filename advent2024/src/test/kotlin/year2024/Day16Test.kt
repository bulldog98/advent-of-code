package year2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day16Test {
    private val day = Day16

    @Test
    fun part1() = assertEquals(7036L, day.testPart1())

    @Test
    fun part2() = assertEquals(45L, day.testPart2())
}