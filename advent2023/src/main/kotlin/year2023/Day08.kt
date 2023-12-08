package year2023

import AdventDay

object Day08: AdventDay(2023, 8) {
    override fun part1(input: List<String>): Any {
        val instructions = input[0]
        val ways = input.drop(2).associate { line ->
            val (node, left, right) = line.split("=", "(", ")", ",").filter { it.isNotBlank() }.map { it.trim() }
            node to (left to right)
        }
        var i = 0
        var node = "AAA"
        while (node != "ZZZ") {
            val (l, r) = ways[node]!!
            val inst = instructions[i % instructions.length]
            i++
            node = when (inst) {
                'R' -> r
                'L' -> l
                else -> error("should not happen")
            }
        }
        return i
    }

    override fun part2(input: List<String>): Any {
        TODO()
    }
}

fun main() = Day08.run()
