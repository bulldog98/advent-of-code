package year2022

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class Day25Test {
    private val day = Day25()

    @Nested
    inner class SnafuNumbers {
        private val numberLookup = listOf(
            1L to "1",
            2L to "2",
            3L to "1=",
            4L to "1-",
            5L to "10",
            6L to "11",
            7L to "12",
            8L to "2=",
            9L to "2-",
            10L to "20",
            15L to "1=0",
            20L to "1-0",
            2022L to "1=11-2",
            12345L to "1-0---0",
            314159265L to "1121-1110-1=0"
        )
        @Test
        fun `correct conversion from snafu`() {
            assertAll(
                numberLookup.map { Executable { assertEquals(it.first, it.second.parseSnafuNumber()) } }
            )
        }
        @Test
        fun `correct conversion to snafu`() {
            assertAll(
                numberLookup.map { Executable { assertEquals(it.second, it.first.toSnafuNumber()) } }
            )
        }
    }

    @Test
    fun part1() = assertEquals(4890L, day.testPart1())


    @Test
    fun part2() = assertEquals("2=-1=0", day.testPart2())
}