package year2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import year2019.computer.IntComputer

class Day02Test {
    @Nested
    @DisplayName("Part1")
    inner class Part1 {
        // example computer
        @Test
        fun `long explained example computer`() {
            val exampleComputer = IntComputer.parse("1,9,10,3,2,3,11,0,99,30,40,50")
            assertEquals(
                listOf(
                    3500L, 9, 10, 70,
                    2, 3, 11, 0,
                    99, 30, 40, 50
                ),
                exampleComputer.simulateUntilHalt()[0..11L]
            )
        }

        @Test
        fun `small example 1`() = assertEquals(
            listOf<Long>(2, 0, 0, 0, 99),
            IntComputer.parse("1,0,0,0,99").simulateUntilHalt()[0..4L]
        )

        @Test
        fun `small example 2`() = assertEquals(
            listOf<Long>(2, 3, 0, 6, 99),
            IntComputer.parse("2,3,0,3,99").simulateUntilHalt()[0..4L]
        )

        @Test
        fun `small example 3`() = assertEquals(
            listOf<Long>(2, 4, 4, 5, 99, 9801),
            IntComputer.parse("2,4,4,5,99,0").simulateUntilHalt()[0..5L]
        )

        @Test
        fun `small example 4`() = assertEquals(
            listOf<Long>(30, 1, 1, 4, 2, 5, 6, 0, 99),
            IntComputer.parse("1,1,1,4,99,5,6,0,99").simulateUntilHalt()[0..8L]
        )
    }
}