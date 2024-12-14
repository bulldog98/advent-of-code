package year2023

import adventday.AdventDay
import adventday.InputRepresentation
import lcm
import year2023.day08.Network

object Day08 : AdventDay(2023, 8) {
    override fun part1(input: InputRepresentation) =
        Network(input)
            .numberOfSteps("AAA") { node ->
                node == "ZZZ"
            }

    // works since all nodes ending with Z can go instruction.length to get to the node again
    override fun part2(input: InputRepresentation): Any {
        val network = Network(input)
        val counts = network.instructions.keys
            .filter { it.last() == 'A' }
            .map { node ->
                network.numberOfSteps(node) { it.last() == 'Z' }
            }
        return lcm(counts)
    }
}

fun main() = Day08.run()
