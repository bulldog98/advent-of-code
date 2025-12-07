package year2015

import adventday.InputRepresentation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day03Test {
    val day = Day03

    @Nested
    @DisplayName("part1")
    inner class Part1 {
        @Test
        fun simple() =
            assertEquals(2, day.part1(InputRepresentation(">")))

        @Test
        fun middle() =
            assertEquals(4, day.part1(InputRepresentation("^>v<")))

        @Test
        fun complex() =
            assertEquals(2, day.part1(InputRepresentation("^v^v^v^v^v")))
    }
}
