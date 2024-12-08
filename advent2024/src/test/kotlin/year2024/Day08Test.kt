package year2024

import Point2D
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day08Test {
    private val day = Day08

    @Test
    fun helper() = assertEquals(
        listOf(Point2D(7, 7), Point2D(10, 10)),
        computeAntinodes(Point2D(8, 8), Point2D(9,9))
    )

    @Test
    fun part1() = assertEquals(14, day.testPart1())
}