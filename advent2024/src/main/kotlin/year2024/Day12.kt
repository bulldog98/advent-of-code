package year2024

import AdventDay
import Point2D
import findAllPositionsOf

fun List<Point2D>.splitInContinuousXParts() = buildList {
    val base = this@splitInContinuousXParts.sortedBy { it.x }.toMutableList()
    while (base.isNotEmpty()) {
        val firstPart = mutableListOf(base.removeFirst())
        while (firstPart.last().x + 1 == base.firstOrNull()?.x) {
            firstPart += base.removeFirst()
        }
        add(firstPart)
    }
}

fun List<Point2D>.splitInContinuousYParts() = buildList {
    val base = this@splitInContinuousYParts.sortedBy { it.y }.toMutableList()
    while (base.isNotEmpty()) {
        val firstPart = mutableListOf(base.removeFirst())
        while (firstPart.last().y + 1 == base.firstOrNull()?.y) {
            firstPart += base.removeFirst()
        }
        add(firstPart)
    }
}

object Day12 : AdventDay(2024, 12) {
    data class Region(val plant: Char, val points: List<Point2D>) {
        val area by lazy {
            points.size.toLong()
        }
        val perimeter by lazy {
            points.flatMap { it.cardinalNeighbors.filter { neighbor -> neighbor !in points } }.size.toLong()
        }
        val numberOfSides by lazy {
            val neighborHood = points.flatMap { it.cardinalNeighbors.filter { neighbor -> neighbor !in points } }.toSet()
            val leftSides = neighborHood.filter { it + Point2D.RIGHT in points }
                .groupBy { it.x }
                .mapValues { it.value.splitInContinuousYParts() }
                .values
                .sumOf { it.size.toLong() }
            val rightSides = neighborHood.filter { it + Point2D.LEFT in points }
                .groupBy { it.x }
                .mapValues { it.value.splitInContinuousYParts() }
                .values
                .sumOf { it.size.toLong() }
            val upSides = neighborHood.filter { it + Point2D.DOWN in points }
                .groupBy { it.y }
                .mapValues { it.value.splitInContinuousXParts() }
                .values
                .sumOf { it.size.toLong() }
            val downSides = neighborHood.filter { it + Point2D.UP in points }
                .groupBy { it.y }
                .mapValues { it.value.splitInContinuousXParts() }
                .values
                .sumOf { it.size.toLong() }
            leftSides + rightSides + upSides + downSides
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

    override fun part1(input: List<String>): Long = input.parseToRegions().sumOf {
        // println("${it.plant}: ${it.area} * ${it.perimeter}")
        it.area * it.perimeter
    }

    override fun part2(input: List<String>): Long = input.parseToRegions().sumOf {
        println("${it.plant}: ${it.area} * ${it.numberOfSides}")
        it.area * it.numberOfSides
    }
}

fun main() = Day12.run()