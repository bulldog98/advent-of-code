package year2019

import Point2D
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day03Test {
    private val day = Day03

    @Nested
    inner class Part1 {
        @Test
        fun `bigger explained example`() {
            val wire1 = Day03.Wire.parse("R8,U5,L5,D3")
            val wire2 = Day03.Wire.parse("U7,R6,D4,L4")
            assertEquals(
                6L,
                (wire1 crosses wire2).minOf { it.manhattanDistance(Point2D.ORIGIN) }
            )
        }
    }

    @Nested
    inner class Part2 {
        @Test
        fun `bigger explained example`() {
            val wire1 = Day03.Wire.parse("R8,U5,L5,D3")
            val wire2 = Day03.Wire.parse("U7,R6,D4,L4")
            assertEquals(
                30L,
                (wire1 crosses wire2).minOf { wire1.distanceOfPoint(it) + wire2.distanceOfPoint(it) }
            )
        }
    }
}