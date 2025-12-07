package year2015

import adventday.InputRepresentation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class Day01Test {
    val day = Day01

    @Nested
    @DisplayName("part1")
    inner class Part1 {
        @ValueSource(strings = ["(())", "()()"])
        @ParameterizedTest
        fun `end floor is floor 0`(instructions: String) =
            assertEquals(0, day.part1(InputRepresentation(instructions)))

        @ValueSource(strings = ["(((", "(()(()(", "))((((("])
        @ParameterizedTest
        fun `end floor is floor 3`(instructions: String) =
            assertEquals(3, day.part1(InputRepresentation(instructions)))

        @ValueSource(strings = ["())", "))("])
        @ParameterizedTest
        fun `end floor is floor -1`(instructions: String) =
            assertEquals(-1, day.part1(InputRepresentation(instructions)))

        @ValueSource(strings = [")))", ")())())"])
        @ParameterizedTest
        fun `end floor is floor -3`(instructions: String) =
            assertEquals(-3, day.part1(InputRepresentation(instructions)))
    }

    @Nested
    @DisplayName("part2")
    inner class Part2 {
        @Test
        fun `first position to reach basement is 1`() =
            assertEquals(1, day.part2(InputRepresentation(")")))


        @Test
        fun `first position to reach basement is 5`() =
            assertEquals(5, day.part2(InputRepresentation("()())")))
    }
}
