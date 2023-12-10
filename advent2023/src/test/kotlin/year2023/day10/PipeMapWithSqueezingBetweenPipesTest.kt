package year2023.day10

import Point2D
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class PipeMapWithSqueezingBetweenPipesTest {
    private fun List<String>.toMap(): Map<Point2D, Char> =
        indices.flatMap { y ->
            this[y].indices.map { x ->
                Point2D(x, y) to this[y][x]
            }
        }.associate { it }

    @Nested
    @DisplayName("computedMap")
    inner class ComputedMap {
        @Test
        fun `correctly compute for single tile`() {
            // not it's important that squeeze through ignores pipes not connected to S
            val expectedLines = listOf(
                "....#....",
                ".##.#....",
                ".#..#....",
                ".#..#....",
                ".#######.",
                "....#..#.",
                ".......#.",
                "...#####.",
                "........."
            )
            val expectedResult = expectedLines.toMap()
            val map = PipeMap.of(listOf("F|.", "LS7", "|-J"))
            val withSqueezingBetweenPipes = PipeMapWithSqueezingBetweenPipes(map)

            val result = withSqueezingBetweenPipes.computedMap

            Assertions.assertEquals(
                expectedResult,
                result
            )
        }
    }
}