package year2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day12Test {
    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `example 1`() = assertEquals(10L, Day12.testPart1("example1"))

        @Test
        fun `example 2`() = assertEquals(19L, Day12.testPart1("example2"))

        @Test
        fun `example 3`() = assertEquals(226L, Day12.testPart1("example3"))
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `example 1`() = assertEquals(36L, Day12.testPart2("example1"))

        @Test
        fun `example 2`() = assertEquals(103L, Day12.testPart2("example2"))

        @Test
        fun `example 3`() = assertEquals(3509L, Day12.testPart2("example3"))
    }
}