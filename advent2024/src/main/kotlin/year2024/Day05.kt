package year2024

import adventday.AdventDay
import adventday.InputRepresentation
import helper.numbers.toAllLongs
import helper.pair.mapFirst
import helper.pair.mapSecond

fun List<String>.getPageOrderRules(): List<Pair<Long, Long>> = map { orderRule ->
        val (a, b) = orderRule.split("|").map { it.toLong() }
        a to b
    }

fun List<String>.getPageOrderings(): List<List<Long>> =map { it.toAllLongs().toList() }

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

fun InputRepresentation.getPageOrderRulesAndOrdering() = asTwoBlocks()
    .mapFirst { it.lines.getPageOrderRules() }
    .mapSecond { it.lines.getPageOrderings() }

object Day05 : AdventDay(2024, 5, "Print Queue") {
    override fun part1(input: InputRepresentation): Long {
        val (orderRules, orderings) = input.getPageOrderRulesAndOrdering()

        return orderings.filter {
            it.windowed(2).all { (a, b) ->
                a to b in orderRules
            }
        }.sumOf {
            it[it.size / 2]
        }
    }

    override fun part2(input: InputRepresentation): Long {
        val (orderRules, orderings) = input.getPageOrderRulesAndOrdering()

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