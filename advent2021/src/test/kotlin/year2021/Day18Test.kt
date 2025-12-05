package year2021

import adventday.InputFiles
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import year2021.Day18.sumOf
import year2021.day18.SnailFishNumber

class Day18Test {
    private val data = InputFiles(2021, 18)
    private fun sumUp(suffix: String) = data.getFileWithSuffix(suffix).lines.map { it: String ->
        SnailFishNumber.parse(it)
    }.sumOf()
    fun sumUpExample(number: Int) = sumUp("example$number")
    fun sumUpExample(name: String) = sumUp("example_$name")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `example 1`() = assertEquals(
            "[[[[1,1],[2,2]],[3,3]],[4,4]]",
            sumUpExample(1).toString()
        )

        @Test
        fun `example 2`() = assertEquals(
            "[[[[3,0],[5,3]],[4,4]],[5,5]]",
            sumUpExample(2).toString()
        )

        @Test
        fun `example 3`() = assertEquals(
            "[[[[5,0],[7,4]],[5,5]],[6,6]]",
            sumUpExample(3).toString()
        )

        @Test
        fun `slightly larger example`() = assertEquals(
            "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]",
            sumUpExample("slightly_larger").toString()
        )

        @Test
        fun part1() = assertEquals(
            4140,
            Day18.testPart1()
        )
    }

    @Test
    fun part2() = assertEquals(3993, Day18.testPart2())
}