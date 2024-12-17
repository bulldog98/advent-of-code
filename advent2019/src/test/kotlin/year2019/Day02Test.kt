package year2019

import adventday.InputRepresentation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import year2019.computer.IntComputer

class Day02Test {
    private val day = Day02

    // example computer
    @Test
    fun part1() {
        val exampleComputer = IntComputer.parse(
            InputRepresentation(
                "1,9,10,3,2,3,11,0,99,30,40,50"
            )
        )
        assertEquals(
            listOf(3500L,9,10,70,
                2,3,11,0,
                99,
                30,40,50),
            exampleComputer.simulateUntilHalt().let { computer ->
                (0..11L).map { i -> computer[i] }
            }
        )
    }
}