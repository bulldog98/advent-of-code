package year2015

import adventday.AdventDay
import adventday.InputRepresentation

class Day14(val winningTime: Int) : AdventDay(2015, 14, "Reindeer Olympics") {
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

    fun List<Reindeer>.scoreAfter(seconds: Int): Map<Reindeer, Int> {
        val score = associate { it to 0 }.toMutableMap()
        val firstRoundWinner = maxBy { it.distanceAfter(1) }
        score[firstRoundWinner] = score[firstRoundWinner]!! + 1
        (1..seconds).forEach { second ->
            val winner = maxBy { it.distanceAfter(second) }
            score[winner] = score[winner]!! + 1
        }
        return score
    }

    override fun part1(input: InputRepresentation): Int = input
        .lines
        .map(Reindeer::parse)
        .maxOf {
            it.distanceAfter(winningTime)
        }

    override fun part2(input: InputRepresentation): Int  = input
        .lines
        .map(Reindeer::parse)
        .scoreAfter(winningTime)
        .maxOf { it.value }
}

fun main() = Day14(2503).run()
