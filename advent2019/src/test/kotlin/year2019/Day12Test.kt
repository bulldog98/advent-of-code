package year2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day12Test {
    private val dayFor10steps = Day12(10)
    private val dayFor100steps = Day12(100)

    @Nested
    @DisplayName("part1")
    inner class Part1 {
        @Test
        fun `example 1 for 10 steps`() = assertEquals(179L, dayFor10steps.testPart1("example1"))

        @Test
        fun `example 2 for 100 steps`() = assertEquals(1940L, dayFor100steps.testPart1("example2"))
    }

    @Disabled("not implemented yet")
    @Nested
    @DisplayName("part2")
    inner class Part2 {
        @Test
        fun `example 1 takes 2772 steps to repeat`() =
            assertEquals(2772L, dayFor10steps.testPart2("example1"))

        @Test
        fun `example 2 takes 4686774924 steps to repeat`() =
            assertEquals(4_686_774_924L, dayFor10steps.testPart2("example2"))
    }
}