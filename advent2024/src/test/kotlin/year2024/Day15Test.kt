package year2024

import Point2D
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class Day15Test {
    private val day = Day15

    @Test
    fun part1() = assertEquals(10092L, day.testPart1())

    @Test
    fun helperBigBox() = assertTrue(
        Day15.BigBox(Point2D(10, 8), Point2D(11, 8)) in Day15.BigBox(
            Point2D(11, 8),
            Point2D(12, 8)
        )
    )

    @Test
    fun part2() = assertEquals(9021L, day.testPart2())
}