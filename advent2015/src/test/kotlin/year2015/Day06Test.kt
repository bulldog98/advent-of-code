package year2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day06Test {
    val day = Day06
    // the example are just the different examples in one file

    @Test
    fun part1() = assertEquals(998996, day.testPart1())
}
