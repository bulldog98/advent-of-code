package year2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day11Test {
    val day = Day11


    // test first example that succeeds
    @Test
    fun part1() = assertEquals("abcdffaa", day.testPart1())
}
