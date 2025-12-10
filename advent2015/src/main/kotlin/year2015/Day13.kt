package year2015

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

    fun InputRepresentation.computeAssignmentsAndPersons() = lines
        .map(Assignment::parse)
        .let { assignments ->
            assignments to assignments.map { it.personA }.distinct()
        }

    fun List<String>.maxScore(assignments: List<Assignment>): Long =
        allOrderings()
            .maxOf { persons ->
                persons
                    .score(assignments)
            }

    override fun part1(input: InputRepresentation): Long = input
        .computeAssignmentsAndPersons()
        .let { (assignments, persons) ->
            persons.maxScore(assignments)
        }

    override fun part2(input: InputRepresentation): Long = input
        .computeAssignmentsAndPersons()
        .let { (assignments, persons) ->
            val myself = "Myself"
            val personsIncludingMyself = persons + myself
            val assignmentsAlsoMyself = assignments + persons.flatMap {
                listOf(
                    Assignment(myself, it, 0),
                    Assignment(it, myself, 0),
                )
            }
            personsIncludingMyself.maxScore(assignmentsAlsoMyself)
        }
}

fun main() = Day13.run()
