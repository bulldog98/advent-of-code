package year2023

import adventday.AdventDay
import helper.numbers.parseAllInts
import kotlin.math.max
import kotlin.math.min

object Day19 : AdventDay(2023, 19) {
    enum class MetalPart(val symbol: Char) {
        ExtremelyCoolLooking('x'),
        Musical('m'),
        Aerodynamic('a'),
        Shiny('s'),
    }

    enum class CompareOperation(val symbol: Char, val compare: (Int, Int) -> Boolean) {
        Greater('>', { a, b -> a > b }),
        LessThan('<', { a, b -> a < b });

        fun nonAcceptedRange(compareTo: Int): IntRange = when (this) {
            Greater -> (1..compareTo)
            LessThan -> (compareTo..4000)
        }

        fun acceptedRange(compareTo: Int): IntRange = when (this) {
            Greater -> compareTo + 1..4000
            LessThan -> 1..<compareTo
        }
    }

    data class Workflow(val name: String, val rules: List<Rule>, val defaultRule: String) {
        companion object {
            fun of(input: String) =
                Workflow(
                    input.split("{")[0],
                    input.split("{")[1].dropLast(1).split(",").dropLast(1).map { Rule.of(it) },
                    input.split(",").last().dropLast(1)
                )
        }
    }

    data class Rule(val metalPart: MetalPart, val compare: CompareOperation, val compareTo: Int, val jumpTo: String) {
        companion object {
            fun of(input: String): Rule =
                Rule(
                    MetalPart.entries.first { it.symbol == input.first() },
                    CompareOperation.entries.first { it.symbol == input[1] },
                    input.parseAllInts().first(),
                    input.split(":")[1]
                )
        }
    }

    data class State(val backingMap: Map<MetalPart, Int>) : Map<MetalPart, Int> by backingMap {
        val rating: Int
            get() = backingMap.entries.sumOf { it.value }

        private fun applyWorkflow(workflow: Workflow): String =
            workflow.rules.firstOrNull {
                it.compare.compare(this[it.metalPart]!!, it.compareTo)
            }?.jumpTo ?: workflow.defaultRule

        fun isAcceptedBy(workflows: Map<String, Workflow>): Boolean =
            generateSequence("in") { last ->
                val currentRule = workflows[last] ?: error("could not find rule of name $last")
                applyWorkflow(currentRule)
            }.first { it == "A" || it == "R" } == "A"

        companion object {
            fun of(input: String): State =
                MetalPart.entries
                    .zip(
                        input
                            .parseAllInts()
                            .toList()
                    ).associate { it }
                    .let {
                        State(it)
                    }
        }
    }

    private fun List<String>.parse(): Pair<Map<String, Workflow>, List<State>> =
        Pair(
            takeWhile { it.isNotEmpty() }
                .map { Workflow.of(it) }
                .groupBy { it.name }
                .mapValues { it.value.single() },
            takeLastWhile { it.isNotEmpty() }
                .map { State.of(it) }
        )

    // we want to produce emptyRang
    @Suppress("EmptyRange")
    private fun IntRange.intersection(other: IntRange): IntRange = when {
        this.last < other.first || other.last < this.first -> 1..-1
        this.first <= other.first && other.last <= this.last -> other
        other.first <= this.first && this.last <= other.last -> this
        else -> max(this.first, other.first)..min(this.last, other.last)
    }

    private fun Workflow.computeSplitting(ranges: Map<MetalPart, IntRange>): List<Pair<String, Map<MetalPart, IntRange>>> =
        buildList {
            val currentRanges = ranges.toMutableMap()
            rules.forEach { rule ->
                this += rule.jumpTo to currentRanges.mapValues {
                    if (it.key == rule.metalPart) {
                        val acceptedRange = it.value.intersection(rule.compare.acceptedRange(rule.compareTo))
                        currentRanges[it.key] = it.value.intersection(rule.compare.nonAcceptedRange(rule.compareTo))
                        acceptedRange
                    } else
                        it.value
                }
            }
            this += defaultRule to currentRanges
        }

    override fun part1(input: List<String>): Int {
        val (workflows, states) = input.parse()
        return states.sumOf {
            if (it.isAcceptedBy(workflows))
                it.rating
            else
                0
        }
    }

    override fun part2(input: List<String>): Long {
        val (workflows) = input.parse()
        val acceptedStates =
            generateSequence(listOf("in" to MetalPart.entries.associateWith { (1..4000) })) { currentPartition ->
                currentPartition
                    .filter { it.first != "R" }
                    .flatMap { (state, ranges) ->
                        if (state == "A")
                            listOf(state to ranges)
                        else
                            workflows[state]?.computeSplitting(ranges) ?: error("workflow $state not found")
                    }
            }.first { it.all { (state) -> state == "A" } }
        return acceptedStates.sumOf { (_, ranges) ->
            ranges.entries.fold(1L) { acc, ent ->
                acc * ent.value.map { it }.size
            }
        }
    }
}


fun main() = Day19.run()
