package year2024

import AdventDay
import Point2D
import findAllPositionsOf

object Day12 : AdventDay(2024, 12) {
    data class Region(val plant: Char, val points: List<Point2D>) {
        val area by lazy {
            points.size.toLong()
        }
        val perimeter by lazy {
            points.flatMap { it.cardinalNeighbors.filter { neighbor -> neighbor !in points } }.size.toLong()
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

    override fun part1(input: List<String>): Long {
        val plants = input.flatMap { it.toSet() }.toSet()
        return plants.flatMap { plant ->
            val plots = input.findAllPositionsOf(plant)
            plots.splitIntoConnectedPlot(input).map { plot ->
                Region(plant, plot)
            }
        }.sumOf {
            // println("${it.plant}: ${it.area} * ${it.perimeter}")
            it.area * it.perimeter
        }
    }

    override fun part2(input: List<String>): Long {
        TODO("Not yet implemented")
    }
}

fun main() = Day12.run()