package year2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import year2019.computer.IntComputer

class Day09Test {
    @Nested
    inner class Part1 {
        @Test
        fun `self replicating program`() {
            val program = listOf<Long>(109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99)
            assertEquals(
                program,
                IntComputer.parseAsFunctionWithArbitraryOutputAndInput(program)(emptyList())
            )
        }

        @Test
        fun `output BigNumbers`() {
            val program = listOf<Long>(1102,34915192,34915192,7,4,7,99,0)
            assertEquals(
                listOf(1219070632396864),
                IntComputer.parseAsFunctionWithArbitraryOutputAndInput(program)(emptyList())
            )
        }

        @Test
        fun `output large number`() {
            val program = listOf<Long>(104,1125899906842624,99)
            assertEquals(
                1125899906842624,
                IntComputer.parseAsFunctionWithArbitraryOutputAndInput(program)(emptyList()).single()
            )
        }
    }
}