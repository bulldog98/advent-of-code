package year2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import year2019.computer.IntComputer

class Day05Test {
    @Nested
    inner class Part1 {
        @Test
        fun `example that explains parameter modes`() {
            val exampleComputer = IntComputer.parse("1002,4,3,4,33")
            assertEquals(
                listOf<Long>(
                    1002,4,3,4,99
                ),
                exampleComputer.computeOneStep()[0..4L]
            )
        }

        @Test
        fun `negative additions work`() {
            val exampleComputer = IntComputer.parse("1101,100,-1,4,0")
            assertEquals(
                99,
                exampleComputer.computeOneStep()[4]
            )
        }
    }
}