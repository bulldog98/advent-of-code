package year2023

import AdventDay
import helper.numbers.parseAllInts

object Day19 : AdventDay(2023, 19) {
    enum class MetalPart(val symbol: Char) {
        ExtremelyCoolLooking('x'),
        Musical('m'),
        Aerodynamic('a'),
        Shiny('s'),
    }

    enum class CompareOperation(val symbol: Char, val compare: (Int, Int) -> Boolean) {
        Greater('>', { a, b -> a > b }),
        LessThan('<', { a, b -> a < b }),
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

    override fun part1(input: List<String>): Int {
        val workflows = input
            .takeWhile { it.isNotEmpty() }
            .map { Workflow.of(it) }
            .groupBy { it.name }
            .mapValues { it.value.single() }
        val states = input
            .takeLastWhile { it.isNotEmpty() }
            .map { State.of(it) }
        return states.sumOf {
            if (it.isAcceptedBy(workflows))
                it.rating
            else
                0
        }
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day19.run()
