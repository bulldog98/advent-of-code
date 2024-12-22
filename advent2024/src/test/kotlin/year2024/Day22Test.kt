package year2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day22Test {
    private val day = Day22

    @Test
    fun part1() = assertEquals(37327623L, day.testPart1("test1"))

    @Test
    fun part2() = assertEquals(23L, day.testPart2("test2"))
}