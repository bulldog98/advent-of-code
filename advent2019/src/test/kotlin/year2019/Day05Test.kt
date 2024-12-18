package year2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import year2019.computer.IntComputer

class Day05Test {
    private val day = Day05

    @Test
    fun part1() {
        val exampleComputer = IntComputer.parse("1002,4,3,4,33")
        assertEquals(
            listOf<Long>(
                1002,4,3,4,99
            ),
            exampleComputer.computeOneStep()[0..4L]
        )
    }
}