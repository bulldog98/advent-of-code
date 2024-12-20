package year2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class Day10Test {
    private val day = Day10

    @Nested
    inner class Part1 {
        @Test
        fun `explained example`() = assertEquals(
            8L,
            day.testPart1("first_example")
        )
        @Test
        fun `larger example 1`() = assertEquals(
            33L,
            day.testPart1("larger_example1")
        )
        @Test
        fun `larger example 2`() = assertEquals(
            35L,
            day.testPart1("larger_example2")
        )
    }

    @Nested
    inner class Part2 {
        @Test
        fun `explained example`() {
            assertThrows<Exception> {
                day.testPart2("part2_example")
            }
        }

        @Test
        fun `largest example`() = assertEquals(
            802L,
            day.testPart2("largest_example")
        )
    }
}