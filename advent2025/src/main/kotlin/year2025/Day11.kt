package year2025

import adventday.AdventDay
import adventday.InputRepresentation

object Day11 : AdventDay(2025, 11, "Reactor") {
    val splitRegex = Regex(""" |: """)

    fun InputRepresentation.asLookupMap() = lines
        .map { it.split(splitRegex) }
        .associate { it[0] to it.drop(1) }

    fun Map<String, List<String>>.countPathsFrom(
        node: String,
        visitedDac: Boolean,
        visitedFft: Boolean,
        visited: MutableMap<Triple<String, Boolean, Boolean>, Long> = mutableMapOf(),
        conditionForValidPath: (Boolean, Boolean) -> Boolean = Boolean::and
    ): Long =
        visited.getOrPut(Triple(node, visitedDac, visitedFft)) {
            fun countNextPaths(
                visited: MutableMap<Triple<String, Boolean, Boolean>, Long>,
                node: String,
                visitedDac: Boolean,
                visitedFft: Boolean,
                conditionForValidPath: (Boolean, Boolean) -> Boolean
            ) = this[node].orEmpty().sumOf { countPathsFrom(it, visitedDac, visitedFft, visited, conditionForValidPath) }
            when (node) {
                "dac" -> countNextPaths(visited, node, visitedDac = true, visitedFft, conditionForValidPath)
                "fft" -> countNextPaths(visited, node, visitedDac, visitedFft = true, conditionForValidPath)
                "out" if (conditionForValidPath(visitedDac, visitedFft)) -> 1L
                else -> countNextPaths(visited, node, visitedDac, visitedFft, conditionForValidPath)
            }
        }

    override fun part1(input: InputRepresentation): Long = input
        .asLookupMap()
        .countPathsFrom("you", visitedDac = false, visitedFft = false) { _, _ -> true }

    override fun part2(input: InputRepresentation): Long = input
        .asLookupMap()
        .countPathsFrom("svr", visitedDac = false, visitedFft = false)
}

fun main() = Day11.run()
