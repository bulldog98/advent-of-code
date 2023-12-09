package numbers

import helper.numbers.NUMBERS_REGEX
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class NumbersTest {
    val testInput1 = """33, \n -55, 66.7"""
    @Nested
    @DisplayName("NUMBERS_REGEX")
    inner class NumbersRegex {

        @Test
        fun `parses TestInput1 Correctly`() {
            val matches = NUMBERS_REGEX.findAll(testInput1).map { it.value }

            Assertions.assertEquals(listOf("33", "-55", "66", "7"), matches.toList())
        }
    }
}