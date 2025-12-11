package year2025

import adventday.AdventDay
import adventday.InputRepresentation

object Day11 : AdventDay(2025, 11, "Reactor") {
    val splitRegex = Regex(""" |: """)

    fun <M> InputRepresentation.asGraphWithMemory(
        memory: M,
        isFinal: (M) -> Boolean = { _ -> true },
        adjustMemory: (String, M) -> M = { _, memory -> memory },
    ) = lines
        .map { it.split(splitRegex) }
        .associate { it[0] to it.drop(1) }
        .let { graphWithoutMemory ->
            GraphWithMemory(
                graphWithoutMemory = graphWithoutMemory,
                initialMemory = memory,
                adjustMemory = adjustMemory,
                isFinal = isFinal,
            )
        }

    data class GraphWithMemory<M>(
        val graphWithoutMemory: Map<String, List<String>>,
        val initialMemory: M,
        val adjustMemory: (String, M) -> M = { _, memory -> memory },
        val isFinal: (M) -> Boolean = { _ -> false },
    ) {
        fun countPathsFromToOut(from: String): Long = mutableMapOf<Pair<String, M>, Long>()
            .memoizedCountPathsFromToOut(from, initialMemory)

        private fun MutableMap<Pair<String, M>, Long>.memoizedCountPathsFromToOut(from: String, memory: M): Long =
            getOrPut(from to memory) {
                when (from) {
                    "out" if (isFinal(memory)) -> 1L
                    else -> graphWithoutMemory[from]
                        .orEmpty()
                        .sumOf { memoizedCountPathsFromToOut(it, adjustMemory(from, memory)) }
                }
            }
    }

    // part 1 was solvable without memoization
    override fun part1(input: InputRepresentation): Long = input
        .asGraphWithMemory(1)
        .countPathsFromToOut("you")

    override fun part2(input: InputRepresentation): Long = input
        .asGraphWithMemory(false to false, { (a, b) -> a && b }) { node, (a, b) ->
            when (node) {
                "fft" -> a to true
                "dac" -> true to b
                else -> a to b
            }
        }
        .countPathsFromToOut("svr")
}

fun main() = Day11.run()
