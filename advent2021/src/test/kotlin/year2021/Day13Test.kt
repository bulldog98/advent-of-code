package year2021

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day13Test {
    @Test
    fun part1() = Assertions.assertEquals(17, Day13.testPart1())
    @Test
    fun part2() = Assertions.assertEquals(
        """
            #####
            #...#
            #...#
            #...#
            #####
        """.trimIndent(),
        Day13.testPart2()
    )
}