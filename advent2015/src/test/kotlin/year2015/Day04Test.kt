package year2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day04Test {
    val day = Day04

    @Nested
    @DisplayName("part1")
    inner class Part1 {
        @Test
        fun example1() =
            assertEquals(609043, day.testPart1("test1"))

        @Test
        fun example2() =
            assertEquals(1048970, day.testPart1("test2"))
    }
}
