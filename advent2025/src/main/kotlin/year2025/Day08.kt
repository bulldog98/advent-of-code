package year2025

import Point3D
import adventday.AdventDay
import adventday.InputRepresentation
import collections.pairings
import year2025.day08.CircuitNetwork
import kotlin.math.sqrt

private fun Double.square() = this * this
private infix fun Point3D.distanceTo(other: Point3D) =
    sqrt((x - other.x).toDouble().square() + (y - other.y).toDouble().square() + (z - other.z).toDouble().square())

private fun Collection<Point3D>.shortestPairsSequence(): Sequence<Pair<Point3D, Point3D>> =
    pairings().sortedBy { (a, b) -> a distanceTo b }

private fun Set<CircuitNetwork>.connectWireBetween(junctionBoxA: Point3D, junctionBoxB: Point3D) = when {
    any { junctionBoxA in it && junctionBoxB in it } -> this
    else -> {
        val containsA = find { junctionBoxA in it } ?: error("always findable")
        val containsB = find { junctionBoxB in it } ?: error("always findable")
        this - setOf(containsA, containsB) + CircuitNetwork(
            listOf(
                containsB,
                containsA
            ).flatMap { it.junctionBoxes }.toSet()
        )
    }
}

class Day08(val pairsToConnectInitially: Int) : AdventDay(2025, 8, "Playground") {
    override fun part1(input: InputRepresentation): Long {
        val junctionBoxes = input.lines.map(Point3D::parse)
        val circuitPairs = junctionBoxes.shortestPairsSequence()
        return circuitPairs
            .take(pairsToConnectInitially)
            .fold(junctionBoxes.map(::CircuitNetwork).toSet()) { circuits, (a, b) ->
                circuits.connectWireBetween(a, b)
            }
            .sortedBy { it.size }
            .takeLast(3)
            .fold(1L) { acc, circuit ->
                acc * circuit.size
            }
    }

    override fun part2(input: InputRepresentation): Long {
        val junctionBoxes = input.lines.map(Point3D::parse)
        val circuitPairs = junctionBoxes.shortestPairsSequence()
        val t = circuitPairs.runningFold(
            junctionBoxes.map(::CircuitNetwork).toSet() to (null as? Pair<Point3D, Point3D>?)
        ) { (circuits), (a, b) ->
            circuits.connectWireBetween(a, b) to (a to b)
        }
        return t.first { it.first.size == 1 }.second?.let {
            it.first.x * it.second.x
        } ?: error("should not happen")
    }
}

fun main() = Day08(1000).run()
