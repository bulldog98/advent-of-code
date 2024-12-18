package year2019

import Point2D
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day03Test {
    data class Example(
        val wire1: Day03.Wire,
        val wire2: Day03.Wire,
    )

    val bigExampleWithExplanation = Example(
        Day03.Wire.parse("R8,U5,L5,D3"),
        Day03.Wire.parse("U7,R6,D4,L4")
    )
    val moreExampleNo1 = Example(
        Day03.Wire.parse("R75,D30,R83,U83,L12,D49,R71,U7,L72"),
        Day03.Wire.parse("U62,R66,U55,R34,D71,R55,D58,R83")
    )
    val moreExampleNo2 = Example(
        Day03.Wire.parse("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51"),
        Day03.Wire.parse("U98,R91,D20,R16,D67,R40,U7,R15,U6,R7")
    )

    @Nested
    inner class Part1 {
        private fun Example.computeDistanceMetric() =
            (wire1 crosses wire2).minOf { it.manhattanDistance(Point2D.ORIGIN) }

        @Test
        fun `bigger explained example`() = assertEquals(
            6L,
            bigExampleWithExplanation.computeDistanceMetric()
        )

        @Test
        fun `additional example 1`() = assertEquals(
            159L,
            moreExampleNo1.computeDistanceMetric()
        )

        @Test
        fun `additional example 2`() = assertEquals(
            135L,
            moreExampleNo2.computeDistanceMetric()
        )
    }

    @Nested
    inner class Part2 {
        private fun Example.computeDistanceMetric() =
            (wire1 crosses wire2).minOf { wire1.distanceOfPoint(it) + wire2.distanceOfPoint(it) }

        @Test
        fun `bigger explained example`() = assertEquals(
            30L,
            bigExampleWithExplanation.computeDistanceMetric()
        )

        @Test
        fun `additional example 1`() = assertEquals(
            610L,
            moreExampleNo1.computeDistanceMetric()
        )

        @Test
        fun `additional example 2`() = assertEquals(
            410L,
            moreExampleNo2.computeDistanceMetric()
        )
    }
}