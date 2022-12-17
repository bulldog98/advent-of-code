import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day17Test {
    private val day = Day17()

    @Test
    fun part1() = assertEquals(3068L, day.testPart1())


    @Test
    fun part2() = assertEquals(1_514_285_714_288L, day.testPart2())
}