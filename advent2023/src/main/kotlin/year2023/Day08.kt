package year2023

import AdventDay
import lcm

fun computeNumberOfSteps(
    node: String,
    instructions: String,
    ways: Map<String, Pair<String, String>>,
    endCondition: (String) -> Boolean
): Long {
    var currentNode = node
    var i = 0L
    while (!endCondition(currentNode)) {
        val (l, r) = ways[currentNode]!!
        val inst = instructions[(i % instructions.length).toInt()]
        i++
        currentNode = when (inst) {
            'R' -> r
            'L' -> l
            else -> error("should not happen")
        }
    }
    return i
}

object Day08 : AdventDay(2023, 8) {
    override fun part1(input: List<String>): Any {
        val instructions = input[0]
        val ways = input.drop(2).associate { line ->
            val (node, left, right) = line.split("=", "(", ")", ",").filter { it.isNotBlank() }.map { it.trim() }
            node to (left to right)
        }
        return computeNumberOfSteps("AAA", instructions, ways) { node -> node == "ZZZ" }
    }

    // works since all nodes ending with Z can go instruction.length to get to the node again
    override fun part2(input: List<String>): Any {
        val instructions = input[0]
        val ways = input.drop(2).associate { line ->
            val (node, left, right) = line.split("=", "(", ")", ",").filter { it.isNotBlank() }.map { it.trim() }
            node to (left to right)
        }
        val counts = ways.keys.filter { it.last() == 'A' }
            .map { node ->
                computeNumberOfSteps(node, instructions, ways) {
                    it.last() == 'Z'
                }
            }
        return lcm(counts)
    }
}

fun main() = Day08.run()
