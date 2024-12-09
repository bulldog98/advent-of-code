package year2024

import Point2D
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day08Test {
    private val day = Day08

    @Test
    fun helper() = assertEquals(
        setOf(Point2D(7, 7), Point2D(10, 10)),
        WithoutHarmonies(Point2D(8, 8), Point2D(9,9), 0..12, 0..12).toSet()
    )

    @Test
    fun part1() = assertEquals(14, day.testPart1())

    @Test
    fun part2() = assertEquals(34, day.testPart2())
}