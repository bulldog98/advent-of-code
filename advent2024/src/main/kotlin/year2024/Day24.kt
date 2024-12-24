package year2024

import adventday.AdventDay
import adventday.InputRepresentation
import helper.pair.mapFirst
import helper.pair.mapSecond
import year2024.day24.ComputationTree

object Day24 : AdventDay(2024, 24) {
    private fun List<ComputationTree>.getZComputations(): List<ComputationTree> {
        val computationTreeLookup =
            (this + (this.filter { it.variableName.startsWith('z') }).flatMap {
                listOf(
                    ComputationTree.InputVariable("x" + it.variableName.drop(1)),
                    ComputationTree.InputVariable("y" + it.variableName.drop(1))
                )
            }).associateBy { it.variableName }

        return this.filter { it.variableName.startsWith('z') }
            .sortedBy { it.variableName }
            .map { it.complete(computationTreeLookup) }
    }

    private val cache = mutableMapOf<Int, Set<String>>()
    private fun variableNamesFor(bit: Int) = cache.getOrPut(bit) {
        val bitAsTwoDigitString = "$bit".padStart(2, '0')
        setOf("x$bitAsTwoDigitString", "y$bitAsTwoDigitString")
    }

    private fun ComputationTree.isHalfAdderSumFor(bit: Int): Boolean =
        this is ComputationTree.XorGateComputation &&
            left is ComputationTree.InputVariable && right is ComputationTree.InputVariable &&
            left.variableName in variableNamesFor(bit) && right.variableName in variableNamesFor(bit)

    private fun ComputationTree.isHalfAdderOverflowFor(bit: Int): Boolean =
        this is ComputationTree.AndGateComputation &&
            left is ComputationTree.InputVariable && right is ComputationTree.InputVariable &&
            left.variableName in variableNamesFor(bit) && right.variableName in variableNamesFor(bit)

    private fun ComputationTree.isFullAdderSumFor(bit: Int) = when (bit) {
        1 -> this is ComputationTree.XorGateComputation && (
            (right.isHalfAdderOverflowFor(0) && left.isHalfAdderSumFor(1)) ||
                (left.isHalfAdderOverflowFor(0) && right.isHalfAdderSumFor(1))
            )

        else -> this is ComputationTree.XorGateComputation && (
            (right.isFullAdderOverflow(bit - 1) && left.isHalfAdderSumFor(bit)) ||
                (left.isFullAdderOverflow(bit - 1) && right.isHalfAdderSumFor(bit))
            )
    }

    private fun ComputationTree.isFullAdderInnerOverflowCarryOverflowFor(bit: Int) = when (bit) {
        1 -> this is ComputationTree.AndGateComputation && (
            (left.isHalfAdderSumFor(bit) && right.isHalfAdderOverflowFor(0)) ||
                (right.isHalfAdderSumFor(bit) && left.isHalfAdderOverflowFor(0))
            )
        else -> this is ComputationTree.AndGateComputation && (
            (left.isHalfAdderSumFor(bit) && right.isFullAdderOverflow(bit - 1)) ||
                (right.isHalfAdderSumFor(bit) && left.isFullAdderOverflow(bit - 1))
            )
    }

    private fun ComputationTree.isFullAdderOverflow(bit: Int): Boolean = this is ComputationTree.OrGateComputation && (
        (left.isHalfAdderOverflowFor(bit) && right.isFullAdderInnerOverflowCarryOverflowFor(bit)) ||
            (right.isHalfAdderOverflowFor(bit) && left.isFullAdderInnerOverflowCarryOverflowFor(bit))
        )

    override fun part1(input: InputRepresentation): Long = input.asTwoBlocks()
        .mapSecond { line -> line.map { ComputationTree.pares(it) }.getZComputations().reversed() }
        .mapFirst { line ->
            line.associateBy({ it.split(": ")[0] }) {
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
        val initialComputations = input.asTwoBlocks().second.map { ComputationTree.pares(it) }

        val exchanging = buildList<Pair<String, String>> {
            var zComputations = fold(initialComputations) { comp, (switchA, switchB) ->
                comp.map { it.rewireOutput(switchA, switchB) }
            }.getZComputations()

            while (zComputations.filterIndexed { i, it -> !((i == 0 && it.isHalfAdderSumFor(0) || it.isFullAdderSumFor(i))) }.isNotEmpty()) {
                val invalidComputation = zComputations.filterIndexed { i, it ->
                    !((i == 0 && it.isHalfAdderSumFor(0) || it.isFullAdderSumFor(i)))
                }.first()

                // now that we know that this addition Bit is wrong, we have to find what is wrong in computation
                TODO()
            }
        }
        return exchanging.flatMap { listOf(it.first, it.second) }.sorted().joinToString(",")
    }
}

fun main() = Day24.run()