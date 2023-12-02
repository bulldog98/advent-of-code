package year2022

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day11Test {
    private val day = Day11()

    @Test
    fun part1() = assertEquals(10605, day.testPart1())


    @Test
    fun part2() = assertEquals(
        2713310158,
        day.testPart2()
    )
}