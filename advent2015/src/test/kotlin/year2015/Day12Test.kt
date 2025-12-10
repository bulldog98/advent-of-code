package year2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day12Test {
    val day = Day12

    // first example of part 1
    @Test
    fun part1() = assertEquals(6L, day.testPart1("example1.1"))

    @Nested
    @DisplayName("part2")
    inner class Part2 {
        @Test
        fun `same example as part1`() = assertEquals(6L, day.testPart2("example1.1"))

        @Test
        fun `2nd part 2 example`() = assertEquals(4L, day.testPart2("example2.1"))
    }
}
