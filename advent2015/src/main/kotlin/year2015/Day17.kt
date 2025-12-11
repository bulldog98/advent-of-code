package year2015

import NotYetImplemented
import adventday.AdventDay
import adventday.InputRepresentation
import helper.numbers.toAllLongs

class Day17(val litersOfEggnog: Int) : AdventDay(2015, 17, "No Such Thing as Too Much") {
    fun <T> List<T>.allSubsets(): Sequence<List<T>> = when {
        isEmpty() -> sequenceOf(listOf())
        else -> sequence {
            val t = this@allSubsets.drop(1).allSubsets()
            yieldAll(t)
            yieldAll(t.map { listOf(this@allSubsets.first()) + it })
        }
    }

    override fun part1(input: InputRepresentation): Int = input
        .text
        .toAllLongs()
        .toList()
        .allSubsets()
        .count { it.sum() == litersOfEggnog.toLong() }

    override fun part2(input: InputRepresentation): Any =
        NotYetImplemented
}

fun main() = Day17(150).run()
