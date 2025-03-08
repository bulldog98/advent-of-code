package year2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day12Test {
    private val dayFor10steps = Day12(10)
    private val dayFor100steps = Day12(100)

    @Test
    fun part1Test1() = assertEquals(179L, dayFor10steps.testPart1("example1"))

    @Test
    fun part1Test2() = assertEquals(1940L, dayFor100steps.testPart1("example2"))
}