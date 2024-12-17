package year2023

import adventday.InputRepresentation
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day20Test {
    @Test
    fun part1() = Assertions.assertEquals(11687500L, Day20.testPart1("test1"))
    // the example for part2 is not perfect, since in real input,
    // it seems like rx has a conjunction module as input with multiple inputs
    @Test
    fun part2() = Assertions.assertEquals(512L, Day20.part2(
        InputRepresentation("""
            broadcaster -> a
            %a -> b
            %b -> c
            %c -> d
            %d -> e
            %e -> f
            %f -> g
            %g -> h
            %h -> i
            %i -> j
            %j -> k
            &k -> rx
        """.trimIndent())
    ))
}
