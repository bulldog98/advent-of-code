package year2021

import adventday.InputFiles
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import year2021.day16.Literal
import year2021.day16.Operator
import year2021.day16.OperatorFunction
import year2021.day16.Packet

class Day16Test {
    private val testData = InputFiles(2021, 16)
    private fun parseExample(number: Int) = Packet.parseHexString(
        testData.getFileWithSuffix("example$number").asText()
    )

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `can parse literal packets`() = assertEquals(
            Literal(6, 2021),
            parseExample(1)
        )

        @Test
        fun `can parse operator packets with length type ID 0`() = assertEquals(
            Operator(1, 0, OperatorFunction.LESS_THAN, listOf(Literal(6, 10), Literal(2, 20))),
            parseExample(2)
        )


        @Test
        fun `can parse operator packets with length type ID 1`() = assertEquals(
            Operator(7, 1, OperatorFunction.MAX, listOf(
                Literal(version=2, value=1), Literal(version=4, value=2), Literal(version=1, value=3)
            )),
            parseExample(3)
        )

        @Test
        fun `example 1`() = assertEquals(6, Day16.testPart1("example1"))

        @Test
        fun `example 2`() = assertEquals(9, Day16.testPart1("example2"))

        @Test
        fun `example 3`() = assertEquals(14, Day16.testPart1("example3"))

        @Test
        fun `example 4`() = assertEquals(16, Day16.testPart1("example4"))

        @Test
        fun `example 5`() = assertEquals(12, Day16.testPart1("example5"))

        @Test
        fun `example 6`() = assertEquals(23, Day16.testPart1("example6"))

        @Test
        fun `example 7`() = assertEquals(31, Day16.testPart1("example7"))
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `example 8`() = assertEquals(3, parseExample(8).value)

        @Test
        fun `example 9`() = assertEquals(54, parseExample(9).value)

        @Test
        fun `example 10`() = assertEquals(7, parseExample(10).value)

        @Test
        fun `example 11`() = assertEquals(9, parseExample(11).value)

        @Test
        fun `example 12`() = assertEquals(1, parseExample(12).value)

        @Test
        fun `example 13`() = assertEquals(0, parseExample(13).value)

        @Test
        fun `example 14`() = assertEquals(0, parseExample(14).value)

        @Test
        fun `example 15`() = assertEquals(1, parseExample(15).value)
    }
}