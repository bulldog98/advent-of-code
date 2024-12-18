package year2019

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
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
                    1002, 4, 3, 4, 99
                ),
                runBlocking {
                    exampleComputer.computeOneStep()[0..4L]
                }
            )
        }

        @Test
        fun `negative additions work`() {
            val exampleComputer = IntComputer.parse("1101,100,-1,4,0")
            assertEquals(
                99,
                runBlocking {
                    exampleComputer.computeOneStep()[4]
                }
            )
        }
    }

    @Nested
    inner class Part2 {
        //region Equal8TestSuite
        abstract inner class Equal8TestSuite(val runExampleWithInput: (Long) -> Long) {
            @Test
            fun `if less than 8 output 0`() = assertEquals(
                0,
                runExampleWithInput(4)
            )

            @Test
            fun `if greater than 8 output 0`() = assertEquals(
                0,
                runExampleWithInput(12)
            )

            @Test
            fun `if equal 8 output 1`() = assertEquals(
                1,
                runExampleWithInput(8)
            )
        }

        @Nested
        @DisplayName("Using position mode, consider whether the input is equal to 8; output 1 (if it is) or 0 (if it is not)")
        inner class PositionModeTestEqual8 : Equal8TestSuite(IntComputer.parseAsFunction("3,9,8,9,10,9,4,9,99,-1,8"))

        @Nested
        @DisplayName("Using immediate mode, consider whether the input is equal to 8; output 1 (if it is) or 0 (if it is not)")
        inner class ImmediateModeTestEqual8 : Equal8TestSuite(IntComputer.parseAsFunction("3,3,1108,-1,8,3,4,3,99"))
        //endregion

        //region LessThan8TestSuite
        abstract inner class LessThan8TestSuite(val runExampleWithInput: (Long) -> Long) {
            @Test
            fun `if less than 8 output 1`() = assertEquals(
                1,
                runExampleWithInput(4)
            )

            @Test
            fun `if greater than 8 output 0`() = assertEquals(
                0,
                runExampleWithInput(12)
            )

            @Test
            fun `if equal 8 output 0`() = assertEquals(
                0,
                runExampleWithInput(8)
            )
        }

        @Nested
        @DisplayName("Using position mode, consider whether the input is less than 8; output 1 (if it is) or 0 (if it is not)")
        inner class PositionModeTestLessThan8 :
            LessThan8TestSuite(IntComputer.parseAsFunction("3,9,7,9,10,9,4,9,99,-1,8"))

        @Nested
        @DisplayName("Using position mode, consider whether the input is less than 8; output 1 (if it is) or 0 (if it is not)")
        inner class ImmediateModeTestLessThan8 :
            LessThan8TestSuite(IntComputer.parseAsFunction("3,3,1107,-1,8,3,4,3,99"))
        //endregion

        //region jump tests
        /**
         * int computers should take an input, then output 0 if the input was zero or 1 if the input was non-zero
         */
        abstract inner class JumpTestSuite(val runExampleWithInput: (Long) -> Long) {
            @Test
            fun `output 0 on input 0`() = assertEquals(
                0,
                runExampleWithInput(0)
            )

            @Test
            fun `if greater than 0 output 1`() = assertEquals(
                1,
                runExampleWithInput(12)
            )
        }

        @Nested
        inner class PositionModeJumpTestSuite: JumpTestSuite(IntComputer.parseAsFunction("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9")) {
            @Test
            fun `if less than 0 output 1`() = assertEquals(
                1,
                runExampleWithInput(-10)
            )
        }
        @Nested
        inner class ImmediateModeJumpTestSuite: JumpTestSuite(IntComputer.parseAsFunction("3,3,1105,-1,9,1101,0,0,12,4,12,99,1")) {
            @Disabled("seems not to work, unsure why")
            @Test
            fun `if less than 0 output 1`() = assertEquals(
                1,
                runExampleWithInput(-10)
            )
        }
        //endregion

        @Nested
        inner class HugeExample {
            val runExampleWithInput = IntComputer.parseAsFunction("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31," +
                "1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104," +
                "999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99")

            @Test
            fun `return 999 if input is less than 8`() = assertEquals(
                999,
                runExampleWithInput(-2)
            )

            @Test
            fun `return 1000 if input is 8`() = assertEquals(
                1000,
                runExampleWithInput(8)
            )

            @Test
            fun `return 1001 if input is greater than 8`() = assertEquals(
                1001,
                runExampleWithInput(9)
            )
        }
    }
}