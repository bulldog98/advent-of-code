package year2019

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import year2019.Day04.testPwCriteriaPart1
import year2019.Day04.testPwCriteriaPart2
import year2019.Day04.toDigits

class Day04Test {
    @Nested
    inner class Part1 {
        @Test
        fun `first example`() = assertTrue((111111L).toDigits().testPwCriteriaPart1())

        @Test
        fun `second example`() = assertFalse((223450L).toDigits().testPwCriteriaPart1())

        @Test
        fun `third example`() = assertFalse((123789L).toDigits().testPwCriteriaPart1())
    }
    @Nested
    inner class Part2 {
        @Test
        fun `first example`() = assertTrue((112233L).toDigits().testPwCriteriaPart2())

        @Test
        fun `second example`() = assertFalse((123444L).toDigits().testPwCriteriaPart2())

        @Test
        fun `third example`() = assertTrue((111122L).toDigits().testPwCriteriaPart2())

        @Test
        fun `own example found by bug`() = assertFalse((111112L).toDigits().testPwCriteriaPart2())
    }
}