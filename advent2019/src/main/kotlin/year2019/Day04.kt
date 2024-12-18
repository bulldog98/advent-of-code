package year2019

import adventday.AdventDay
import adventday.InputRepresentation

object Day04: AdventDay(2019, 4) {
    fun Long.testPwCriteria() = toString().toList().let { digits ->
        digits.zipWithNext().all { (a, b) -> a <= b } &&
            digits.zipWithNext().any { (a, b) -> a == b }
    }

    override fun part1(input: InputRepresentation): Int {
        val range = input[0].split("-").map { it.toLong() }.let { it[0]..it[1] }
        return range.count {
            it.testPwCriteria()
        }
    }

    override fun part2(input: InputRepresentation): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day04.run()