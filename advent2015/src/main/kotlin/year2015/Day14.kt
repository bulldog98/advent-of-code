package year2015

import NotYetImplemented
import adventday.AdventDay
import adventday.InputRepresentation

object Day14 : AdventDay(2015, 14, "Reindeer Olympics") {
    data class Reindeer(val name: String, val flightSpeed: Int, val flightTime: Int, val restTime: Int) {
        companion object {
            private val regex =
                Regex("([A-Z][a-z]*) can fly (\\d+) km/s for (\\d+) seconds, but then must rest for (\\d+) seconds.")

            fun parse(line: String): Reindeer = regex.find(line)
                ?.groups
                ?.drop(1)
                ?.let { (m1, m2, m3, m4) ->
                    m1 ?: m2 ?: m3 ?: m4 ?: error("Not enough matches for $line")
                    Reindeer(
                        m1!!.value,
                        m2!!.value.toInt(),
                        m3!!.value.toInt(),
                        m4!!.value.toInt()
                    )
                } ?: error("could not match line: $line")
        }

        fun distanceAfter(seconds: Int): Int {
            val cycleTime = flightTime + restTime
            val timeAfterCycleLeft = seconds.mod(cycleTime)
            return flightSpeed * ((seconds / cycleTime) * flightTime + timeAfterCycleLeft.coerceAtMost(flightTime))
        }
    }

    const val FIRST_WINNING_TIME = 2503

    override fun part1(input: InputRepresentation): Int = input
        .lines
        .map(Reindeer::parse)
        .maxOf {
            it.distanceAfter(FIRST_WINNING_TIME)
        }

    override fun part2(input: InputRepresentation): Any =
        NotYetImplemented
}

fun main() = Day14.run()
