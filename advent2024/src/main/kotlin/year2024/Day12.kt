package year2024

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import findAllPositionsOf

sealed interface Axis : (Point2D) -> Long
data object XAxis : Axis {
    override fun invoke(point: Point2D) = point.x
}
data object YAxis : Axis {
    override fun invoke(point: Point2D) = point.y
}

fun Axis.rotate90Degrees() = when (this) {
    XAxis -> YAxis
    YAxis -> XAxis
}

fun List<Point2D>.splitInContinuousParts(axis: Axis) = buildList {
    val base = this@splitInContinuousParts.sortedBy(axis::invoke).toMutableList()
    while (base.isNotEmpty()) {
        val firstPart = mutableListOf(base.removeFirst())
        while (axis(firstPart.last()) + 1 == base.firstOrNull()?.let(axis::invoke)) {
            firstPart += base.removeFirst()
        }
        add(firstPart)
    }
}

object Day12 : AdventDay(2024, 12) {
    data class Region(val plant: Char, val points: List<Point2D>) {
        val area = points.size.toLong()
        val perimeter by lazy {
            points.flatMap { it.cardinalNeighbors.filter { neighbor -> neighbor !in points } }.size.toLong()
        }
        val numberOfSides by lazy {
            val neighborHood = points.flatMap { it.cardinalNeighbors.filter { neighbor -> neighbor !in points } }.toSet()
            listOf(
                Point2D.RIGHT   to XAxis,
                Point2D.LEFT    to XAxis,
                Point2D.UP      to YAxis,
                Point2D.DOWN    to YAxis,
            ).sumOf { (dir, axis) ->
                neighborHood.filter { it + dir in points }
                    .groupBy(axis)
                    .mapValues { it.value.splitInContinuousParts(axis.rotate90Degrees()) }
                    .values
                    .sumOf { it.size.toLong() }
            }
        }
    }

    private operator fun List<String>.get(p: Point2D) = this.getOrNull(p.y.toInt())?.getOrNull(p.x.toInt())
    private fun Collection<Point2D>.splitIntoConnectedPlot(input: List<String>): List<List<Point2D>> = buildList {
        if (this@splitIntoConnectedPlot.isEmpty()) return@buildList
        val rest = this@splitIntoConnectedPlot.toMutableList()

        while (rest.isNotEmpty()) {
            add(buildList {
                add(rest.removeFirst())
                while (rest.any { r -> this.any { r in it.cardinalNeighbors.filter { n -> input[n] == input[r] } } }) {
                    val toRemove =
                        rest.first { r -> this.any { r in it.cardinalNeighbors.filter { n -> input[n] == input[r] } } }
                    add(toRemove)
                    rest -= toRemove
                }
            })
        }
    }

    private fun List<String>.parseToRegions() = flatMap { it.toSet() }.toSet().flatMap { plant ->
        val plots = findAllPositionsOf(plant)
        plots.splitIntoConnectedPlot(this).map { plot ->
            Region(plant, plot)
        }
    }

    override fun part1(input: InputRepresentation): Long = input.lines.parseToRegions().sumOf {
        // println("${it.plant}: ${it.area} * ${it.perimeter}")
        it.area * it.perimeter
    }

    override fun part2(input: InputRepresentation): Long = input.lines.parseToRegions().sumOf {
        // println("${it.plant}: ${it.area} * ${it.numberOfSides}")
        it.area * it.numberOfSides
    }
}

fun main() = Day12.run()