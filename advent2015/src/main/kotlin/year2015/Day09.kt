package year2015

import adventday.AdventDay
import adventday.InputRepresentation
import collections.allOrderings

object Day09 : AdventDay(2015, 9, "All in a Single Night") {
    data class Connection(
        val from: String,
        val to: String,
        val distance: Int
    ) {
        // city can go this path
        operator fun contains(city: String): Boolean =
            city == from || city == to

        companion object {
            fun parse(line: String): Connection {
                val (from, to, distance) = line.split(" = ", " to ")
                return Connection(from, to, distance.toInt())
            }
        }
    }

    private fun List<String>.distanceWith(connections: List<Connection>): Int =
        windowed(2)
            .sumOf { (a, b) ->
                connections.first { a in it && b in it }.distance
            }

    private fun InputRepresentation.toConnectionsAndCities(): Pair<List<Connection>, List<String>> = lines
        .map { Connection.parse(it) }
        .let { it to it.flatMap { (a, b) -> listOf(a, b) }.distinct() }

    override fun part1(input: InputRepresentation): Int = input
        .toConnectionsAndCities()
        .let { (connections, cities) ->
            // every way is possible since all cities are connected and only 8 cities in real input so bruteforce
            cities.allOrderings()
                .minOf {
                    it.distanceWith(connections)
                }
        }

    override fun part2(input: InputRepresentation): Int = input
        .toConnectionsAndCities()
        .let { (connections, cities) ->
            cities.allOrderings()
                .maxOf {
                    it.distanceWith(connections)
                }
        }
}

fun main() = Day09.run()
