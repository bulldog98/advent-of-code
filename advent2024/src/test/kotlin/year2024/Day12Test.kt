package year2024

import Point2D
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day12Test {
    private val day = Day12

    @Test
    fun part1() = assertEquals(1930L, day.testPart1("test1"))

    @Test
    fun correctSideCounting() = assertEquals(
        10L,
        Day12.Region(
            plant = 'R',
            points = listOf(
                Point2D(x = 0, y = 0), Point2D(x = 1, y = 0), Point2D(x = 2, y = 0), Point2D(x = 3, y = 0),
                Point2D(x = 0, y = 1), Point2D(x = 1, y = 1), Point2D(x = 2, y = 1), Point2D(x = 3, y = 1),
                                                              Point2D(x = 2, y = 2), Point2D(x = 3, y = 2), Point2D(x = 4, y = 2),
                                                              Point2D(x = 2, y = 3)
            )
        ).numberOfSides
    )

    @Test
    fun part2() = assertEquals(1206L, day.testPart2("test2"))
}