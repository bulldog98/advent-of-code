package year2023

import adventday.AdventDay
import adventday.InputRepresentation
import lcm
import year2023.day08.Network

object Day08 : AdventDay(2023, 8) {
    override fun part1(input: InputRepresentation) = input.asTwoBlocks().let { (first, second) ->
        Network(first.first(), second)
            .numberOfSteps("AAA") { node ->
                node == "ZZZ"
            }
    }

    // works since all nodes ending with Z can go instruction.length to get to the node again
    override fun part2(input: InputRepresentation): Any {
        val (leftRightOrder, instructionLines) = input.asTwoBlocks()
        val network = Network(leftRightOrder.first(), instructionLines)
        val counts = network.instructions.keys
            .filter { it.last() == 'A' }
            .map { node ->
                network.numberOfSteps(node) { it.last() == 'Z' }
            }
        return lcm(counts)
    }
}

fun main() = Day08.run()
