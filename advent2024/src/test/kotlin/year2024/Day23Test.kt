package year2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day23Test {
    private val day = Day23

    @Test
    fun part1() = assertEquals(7L, day.testPart1())

    @Test
    fun part2() = assertEquals("co,de,ka,ta", day.testPart2())
}