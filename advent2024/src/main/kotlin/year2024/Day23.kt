package year2024

import adventday.AdventDay
import adventday.InputRepresentation
import collections.pairings

object Day23 : AdventDay(2024, 23) {
    override fun part1(input: InputRepresentation): Long {
        val connections = input.map { it.split("-").toSet() }
        val computers = connections.flatten().distinct()
        val triples = computers.flatMap { c1 ->
            val directConnectedComputers = connections.filter { c1 in it }.map { it - c1 }.flatten()
            directConnectedComputers.pairings().filter { (c2, c3) -> setOf(c2, c3) in connections }.map { (c2, c3) ->
                setOf(c1, c2, c3)
            }
        }.distinct()
        return triples.count {
            it.any { com -> com.startsWith('t') }
        }.toLong()
    }

    override fun part2(input: InputRepresentation): Long {
        TODO("Not yet implemented")
    }
}

fun main() = Day23.run()