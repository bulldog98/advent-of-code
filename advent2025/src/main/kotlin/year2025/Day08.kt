package year2025

import NotYetImplemented
import Point3D
import adventday.AdventDay
import adventday.InputRepresentation
import collections.pairings
import kotlin.math.sqrt

data class CircuitNetwork(
    val junctionBoxes: Set<Point3D>
) {
    val size: Int
        get() = junctionBoxes.size

    constructor(junctionBox: Point3D) : this(setOf(junctionBox))

    operator fun contains(junctionBox: Point3D) = junctionBox in junctionBoxes
}

private fun Double.square() = this * this
private fun Point3D.distanceTo(other: Point3D) =
    sqrt((x - other.x).toDouble().square() + (y - other.y).toDouble().square() + (z - other.z).toDouble().square())

private fun List<Point3D>.shortestPairsSequence(): Sequence<Pair<Point3D, Point3D>> =
    generateSequence(setOf<Pair<Point3D, Point3D>>()) { acc ->
        val pairs = this@shortestPairsSequence.pairings().filter { (a, b) -> a to b !in acc && b to a !in acc }
        val (a, b) = pairs.minByOrNull { (a, b) -> a.distanceTo(b) } ?: return@generateSequence null
        val (x, y) = listOf(a, b).sortedBy { (x) -> x }
        acc + setOf(x to y)
    }.zipWithNext()
        .map { (a, b) -> (b - a).first() }

class Day08(val pairsToConnectInitially: Int) : AdventDay(2025, 8, "Playground") {

    override fun part1(input: InputRepresentation): Long {
        val junctionBoxes = input.lines.map(Point3D::parse)
        val circuitPairs = junctionBoxes.shortestPairsSequence()
        return circuitPairs
            .take(pairsToConnectInitially)
            .fold(junctionBoxes.map(::CircuitNetwork).toSet()) { circuits, (a, b) ->
                // connect one wire
                when {
                    circuits.any { a in it && b in it } -> circuits
                    else -> {
                        val containsA = circuits.find { a in it } ?: error("always findable")
                        val containsB = circuits.find { b in it } ?: error("always findable")
                        circuits - setOf(containsA, containsB) + CircuitNetwork(
                            listOf(
                                containsB,
                                containsA
                            ).flatMap { it.junctionBoxes }.toSet()
                        )
                    }
                }
            }
            .sortedBy { it.size }
            .takeLast(3)
            .fold(1L) { acc, circuit ->
                acc * circuit.size
            }
    }

    override fun part2(input: InputRepresentation): Any =
        NotYetImplemented
}

fun main() = Day08(1000).run()
