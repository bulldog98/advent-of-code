package year2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import year2024.day17.powerOf2

class Day17Test {
    private val day = Day17

    @Test
    fun powerOf2Works() = assertAll(
        { assertEquals(1, powerOf2(0)) },
        { assertEquals(2, powerOf2(1)) },
        { assertEquals(4, powerOf2(2)) },
    )

    @Test
    fun part1() = assertEquals("4,6,3,5,6,3,5,2,1,0", day.testPart1())

    // write number as octal to better see patterns is 117440L
    @Test
    fun part2() = assertEquals("345300".toLong(8), day.testPart2())
}