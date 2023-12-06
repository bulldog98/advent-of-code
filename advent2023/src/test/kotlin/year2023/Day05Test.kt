package year2023

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day05Test {
    @Test
    fun part1() = Assertions.assertEquals(35L, Day05.testPart1())

    @Test
    fun stupidCase() {
        val range = 57L..69L
        val mapping = listOf(Mapping( 53L..60L) { it })

        val res = range.splitBy(mapping)

        Assertions.assertEquals(2, res.size)
    }

    @Test
    fun part2() = Assertions.assertEquals(46L, Day05.testPart2())
}