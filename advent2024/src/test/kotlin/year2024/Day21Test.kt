package year2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day21Test {
    private val day = Day21

    @Test
    fun part1() = assertEquals(126384L, day.testPart1())

    @Test
    fun part2() = assertEquals(154115708116294L, day.testPart2())

    /*
        Compare the two paths <^A and ^<A.

        On the next level, the first will change into v<<A>^A>A, and the second into <A<vA>>^A.

        On the next level, the first will change into <vA<AA>>^AvA<^A>AvA^A, and the second into v<<A>>^Av<<A>A^>AvAA<^A>A. These are not the same length.
     */
}