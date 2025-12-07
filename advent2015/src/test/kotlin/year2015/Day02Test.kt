package year2015

import adventday.InputRepresentation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day02Test {
    val day = Day02

    @Nested
    @DisplayName("part1")
    inner class Part1 {
        @Test
        fun `example package 2x3x4`() =
            assertEquals(58, day.part1(InputRepresentation("2x3x4")))


        @Test
        fun `example package 1x1x10`() =
            assertEquals(43, day.part1(InputRepresentation("1x1x10")))
    }
}
