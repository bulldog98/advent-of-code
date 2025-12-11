package year2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day18Test {
    val day = Day18(6, 4)

    @Test
    fun part1() = assertEquals(4, day.testPart1())
}
