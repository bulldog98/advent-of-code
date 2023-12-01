import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day01Test {
    private val day = Day01()

    @Test
    fun part1() = assertEquals(142, day.testPart1())


    @Test
    fun part2() = assertEquals(281, day.testPart2())
}