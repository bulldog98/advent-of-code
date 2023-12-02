package year2022

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day07Test {
    private val day = Day07()

    @Test
    fun part1() = assertEquals(95437, day.testPart1())


    @Test
    fun part2() = assertEquals(24933642, day.testPart2())
}