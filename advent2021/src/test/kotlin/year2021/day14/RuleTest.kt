package year2021.day14

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RuleTest {

    @Test
    fun `double char rule should work`() {
        val input = "14448"
        val rule = Rule('4', '4', '9')

        val results = rule.matchesAtPositions(input)

        Assertions.assertEquals(2, results.toList().size)
    }
}