package year2024

import AdventDay
import helper.numbers.toAllLongs

fun List<String>.getPageOrderRules(): List<Pair<Long, Long>> = takeWhile { it.isNotEmpty() }
    .map {
        val (a, b) = it.split("|").map { it.toLong() }
        a to b
    }

fun List<String>.getPageOrderings(): List<List<Long>> = dropWhile { it.isNotEmpty() }
    .drop(1)
    .map { it.toAllLongs().toList() }

object Day05 : AdventDay(2024, 5) {
    override fun part1(input: List<String>): Long {
        val orderRules = input.getPageOrderRules()
        val orderings = input.getPageOrderings()

        return orderings.filter {
            it.windowed(2).all { (a, b) ->
                a to b in orderRules
            }
        }.sumOf {
            it[it.size/2]
        }
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day05.run()