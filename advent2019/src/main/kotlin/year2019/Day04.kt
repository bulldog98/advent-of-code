package year2019

import adventday.AdventDay
import adventday.InputRepresentation

object Day04: AdventDay(2019, 4) {
    fun List<Char>.testPwCriteriaPart1() = allNotDecreasing() && zipWithNext().any { (a, b) -> a == b }
    fun List<Char>.testPwCriteriaPart2() = allNotDecreasing() && hasGroupOfExactLength2()

    private fun List<Char>.allNotDecreasing() = zipWithNext().all { (a, b) -> a <= b }

    private fun List<Char>.hasGroupOfExactLength2(): Boolean {
        var rest = this.toList()
        while (rest.isNotEmpty()) {
            val charToRemove = rest.first()
            val newRest = rest.dropWhile { it == charToRemove }
            if (rest.size - newRest.size == 2) return true
            rest = newRest
        }
        return false
    }

    fun Long.toDigits() = toString().toList()

    override fun part1(input: InputRepresentation): Int {
        val range = input.lines[0].split("-").map { it.toLong() }.let { it[0]..it[1] }
        return range.count {
            it.toDigits().testPwCriteriaPart1()
        }
    }

    override fun part2(input: InputRepresentation): Int {
        val range = input.lines[0].split("-").map { it.toLong() }.let { it[0]..it[1] }

        return range.count {
                it.toDigits().testPwCriteriaPart2()
            }
    }
}

fun main() = Day04.run()