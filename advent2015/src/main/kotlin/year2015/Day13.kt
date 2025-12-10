package year2015

import NotYetImplemented
import adventday.AdventDay
import adventday.InputRepresentation
import collections.allOrderings
import helper.numbers.toAllLongs

object Day13 : AdventDay(2015, 13, "Knights of the Dinner Table") {
    data class Assignment(val personA: String, val neighbor: String, val happinessChange: Long) {
        companion object {
            fun parse(input: String): Assignment = Assignment(
                input.split(" ")[0],
                input.split(" ").last().dropLast(1),
                input.toAllLongs().first() * if (input.contains("gain")) 1 else -1
            )
        }
    }

    fun List<String>.score(assignments: List<Assignment>): Long {
        val pairings = (this + setOf(first())).windowed(2)
        return pairings.sumOf { (a, b) ->
            assignments.first { it.personA == a && it.neighbor == b }.happinessChange +
                assignments.first { it.personA == b && it.neighbor == a }.happinessChange
        }
    }

    override fun part1(input: InputRepresentation): Long = input
        .lines
        .map(Assignment::parse)
        .let { assignments ->
            val persons = assignments.map { it.personA }.distinct()
            persons.allOrderings()
                .maxOf { it.score(assignments) }
        }

    override fun part2(input: InputRepresentation): Any =
        NotYetImplemented
}

fun main() = Day13.run()
