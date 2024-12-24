package year2024

import adventday.InputRepresentation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day24Test {
    private val day = Day24

    @Test
    fun part1() = assertEquals(4L, day.testPart1("test_part1"))


    @Test
    fun part2() = assertEquals("", day.part2(
        InputRepresentation("""
            x00: 0
            y00: 0
            x01: 0
            y01: 0
            
            x00 XOR y00 -> z00
            x00 AND y00 -> c00
            x01 XOR y01 -> h01
            h01 XOR c00 -> z01
        """.trimIndent())
    ))
}