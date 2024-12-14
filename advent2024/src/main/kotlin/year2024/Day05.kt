package year2024

import adventday.AdventDay
import helper.numbers.toAllLongs

fun List<String>.getPageOrderRules(): List<Pair<Long, Long>> = takeWhile { it.isNotEmpty() }
    .map { orderRule ->
        val (a, b) = orderRule.split("|").map { it.toLong() }
        a to b
    }

fun List<String>.getPageOrderings(): List<List<Long>> = dropWhile { it.isNotEmpty() }
    .drop(1)
    .map { it.toAllLongs().toList() }

class PageOrderRuleComparator(private val pageOrderRules: List<Pair<Long, Long>>) : Comparator<Long> {
    override fun compare(x: Long?, y: Long?): Int = when {
        x == y -> 0
        x to y in pageOrderRules -> -1
        else -> 1
    }

}

private fun List<Long>.orderByRules(orderRules: List<Pair<Long, Long>>): List<Long> {
    val applyingRules = orderRules.filter { (a, b) -> a in this && b in this }
    val pageOrderRuleComparator = PageOrderRuleComparator(applyingRules)

    return this.sortedWith(pageOrderRuleComparator)
}

object Day05 : AdventDay(2024, 5) {
    override fun part1(input: List<String>): Long {
        val orderRules = input.getPageOrderRules()
        val orderings = input.getPageOrderings()

        return orderings.filter {
            it.windowed(2).all { (a, b) ->
                a to b in orderRules
            }
        }.sumOf {
            it[it.size / 2]
        }
    }

    override fun part2(input: List<String>): Any {
        val orderRules = input.getPageOrderRules()
        val orderings = input.getPageOrderings()

        return orderings.filter {
            it.windowed(2).any { (a, b) ->
                b to a in orderRules
            }
        }.map {
            it.orderByRules(orderRules)
        }.sumOf {
            it[it.size / 2]
        }
    }
}

fun main() = Day05.run()