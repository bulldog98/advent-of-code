package year2015

import adventday.InputRepresentation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day01Test {
    val day = Day01

    @Test
    fun part1() = assertEquals(0L, day.part1(InputRepresentation("(())")))

    @Test
    fun part2() = assertEquals(5, day.part2(InputRepresentation("()())")))
}
