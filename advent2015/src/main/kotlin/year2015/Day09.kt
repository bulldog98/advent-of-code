package year2015

import NotYetImplemented
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

    override fun part1(input: InputRepresentation): Int = input
        .lines
        .map(Connection::parse)
        .let { distances ->
            val cities = distances.flatMap { (a, b) -> listOf(a, b) }.distinct()
            cities
                .allOrderings()
                .filter {
                    it.windowed(2).all { (from, to) ->
                        distances.count { connection ->
                            from in connection && to in connection
                        } > 0
                    }
                }
                .minOf {
                    it.windowed(2)
                        .sumOf { (a, b) ->
                            distances.first { a in it && b in it }.distance
                        }
                }
        }

    override fun part2(input: InputRepresentation): Any =
        NotYetImplemented
}

fun main() = Day09.run()
