package year2019

import adventday.InputRepresentation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day02Test {
    private val day = Day02

    // example computer
    @Test
    fun part1() {
        val exampleComputer = IntComputerExecutionState.parse(
            InputRepresentation(
                "1,9,10,3,2,3,11,0,99,30,40,50"
            )
        )
        assertEquals(
            listOf(3500L,9,10,70,
                2,3,11,0,
                99,
                30,40,50),
            exampleComputer.simulateUntilHalt().program
        )
    }
}