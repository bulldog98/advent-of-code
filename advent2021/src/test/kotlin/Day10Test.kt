import day10.scoreCompletion
import day10.toBracketMatcher
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day10Test {
    @Test
    fun part1() = Assertions.assertEquals(26397, Day10.testPart1())

    @Test
    fun part2() = Assertions.assertEquals(288957L, Day10.testPart2())

    @Test
    fun `scoring for part2 works for 1 line`() = with("])}>".map { it.toBracketMatcher() }.scoreCompletion()) {
        Assertions.assertEquals(294L, this)
    }
}
