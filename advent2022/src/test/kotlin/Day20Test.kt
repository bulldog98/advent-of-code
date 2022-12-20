import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day20Test {
    private val day = Day20()

    @Test
    fun part1() = assertEquals(3L, day.testPart1())

    @Test
    fun part2() = assertEquals(1623178306L, day.testPart2())
}