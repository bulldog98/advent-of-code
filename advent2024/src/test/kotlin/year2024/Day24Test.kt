package year2024

import adventday.InputRepresentation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day24Test {
    private val day = Day24

    @Test
    fun part1() = assertEquals(4L, day.testPart1("test_part1"))

    @Nested
    inner class Part2 {
        @Test
        fun `no swaps needed`() = assertEquals("", day.part2(
            InputRepresentation("""
            x00: 0
            y00: 0
            x01: 0
            y01: 0
            
            x00 XOR y00 -> z00
            x00 AND y00 -> c00
            x01 XOR y01 -> i01
            x01 AND y01 -> j01
            c00 XOR i01 -> z01
            i01 AND c00 -> p01
            p01 OR j01 -> z02
        """.trimIndent())
        ))

        @Test
        fun `one swaps needed`() = assertEquals("c00,z00", day.part2(
            InputRepresentation("""
            x00: 0
            y00: 0
            x01: 0
            y01: 0
            
            x00 XOR y00 -> c00
            x00 AND y00 -> z00
            x01 XOR y01 -> i01
            x01 AND y01 -> j01
            c00 XOR i01 -> z01
            i01 AND c00 -> p01
            p01 OR j01 -> z02
        """.trimIndent())
        ))
    }
}