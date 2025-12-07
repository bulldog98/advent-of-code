package year2024

import adventday.AdventDay
import adventday.InputRepresentation
import helper.pair.mapFirst
import helper.pair.mapSecond
import year2024.day24.ComputationTree
import year2024.day24.getInvalidPartForAdditionForBit
import year2024.day24.isValidAdditionForBit

object Day24 : AdventDay(2024, 24, "Crossed Wires") {
    private fun List<ComputationTree>.getCompletedComputations(): List<ComputationTree> {
        val computationTreeLookup =
            (this + (this.filter { it.variableName.startsWith('z') }).flatMap {
                listOf(
                    ComputationTree.InputVariable("x" + it.variableName.drop(1)),
                    ComputationTree.InputVariable("y" + it.variableName.drop(1))
                )
            }).associateBy { it.variableName }

        return this.sortedBy { it.variableName }
            .map { it.complete(computationTreeLookup) }
    }

    private fun List<ComputationTree>.getZComputations(): List<ComputationTree> {
        return getCompletedComputations().filter { it.variableName.startsWith('z') }
    }

    override fun part1(input: InputRepresentation): Long = input.asTwoBlocks()
        .mapSecond { line -> line.lines.map { it: String -> ComputationTree.pares(it) }.getZComputations().reversed() }
        .mapFirst { input ->
            input.lines.associateBy({ it.split(": ")[0] }) {
                val value = it.split(": ")[1]
                when (value) {
                    "1" -> true
                    "0" -> false
                    else -> error("could not parse")
                }
            }
        }.let { (lookup, computations) ->
            computations.joinToString("") {
                if (it.computeValue(lookup::getValue)) "1" else "0"
            }.toLong(2)
        }

    override fun part2(input: InputRepresentation): String {
        val initialComputations = input.asTwoBlocks().second.lines.map { it: String -> ComputationTree.pares(it) }
        val maxBit = initialComputations.filter { it.variableName.startsWith('z') }
            .maxOf { it.variableName.drop(1).toInt() } - 1

        @Suppress("RemoveExplicitTypeArguments") // compiler can not infer, but idea thinks it can
        val exchanging = buildList<Pair<String, String>> {
            var completedComputations = fold(initialComputations) { comp, (switchA, switchB) ->
                comp.map { it.rewireOutput(switchA, switchB) }
            }.getCompletedComputations()
            var zComputations = completedComputations.filter { it.variableName.startsWith('z') }

            while (zComputations.filterIndexed { i, it -> !it.isValidAdditionForBit(i, maxBit) }.isNotEmpty()) {
                val invalidComputation = zComputations.filterIndexed { i, it ->
                    !it.isValidAdditionForBit(i, maxBit)
                }.first()
                val index = zComputations.indexOf(invalidComputation)
                val (variable, checker) = invalidComputation.getInvalidPartForAdditionForBit(index, maxBit)
                    ?: error("could not compute")

                val otherVar = completedComputations.single { it.checker(index) }.variableName
                add(variable to otherVar)
                completedComputations = fold(initialComputations) { comp, (switchA, switchB) ->
                    comp.map { it.rewireOutput(switchA, switchB) }
                }.getCompletedComputations()
                zComputations = completedComputations.filter { it.variableName.startsWith('z') }
            }
        }
        return exchanging.flatMap { listOf(it.first, it.second) }.sorted().joinToString(",")
    }
}

fun main() = Day24.run()