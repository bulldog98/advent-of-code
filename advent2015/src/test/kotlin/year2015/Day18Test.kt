package year2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day18Test {
    val dayWithEvaluateAfter4Steps = Day18(6, 4)
    val dayWithEvaluateAfter5Steps = Day18(6, 5)

    @Test
    fun part1() = assertEquals(4, dayWithEvaluateAfter4Steps.testPart1())

    @Test
    fun part2() = assertEquals(17, dayWithEvaluateAfter5Steps.testPart2())
}
