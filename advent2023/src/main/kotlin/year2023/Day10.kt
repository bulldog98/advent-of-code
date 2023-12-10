package year2023

import AdventDay
import Point2D
import graph.AdjacencyListGraph
import graph.dijkstra

object Day10 : AdventDay(2023, 10) {
    private fun Char.isConnectedTo(point2D: Point2D): Boolean =
        point2D in connectedTo()

    private fun Char.connectedTo(): Set<Point2D> = when (this) {
        '|' -> setOf(Point2D.UP, Point2D.DOWN)
        '-' -> setOf(Point2D.LEFT, Point2D.RIGHT)
        'L' -> setOf(Point2D.UP, Point2D.RIGHT)
        'J' -> setOf(Point2D.UP, Point2D.LEFT)
        '7' -> setOf(Point2D.LEFT, Point2D.DOWN)
        'F' -> setOf(Point2D.RIGHT, Point2D.DOWN)
        'S' -> setOf(Point2D.UP, Point2D.DOWN, Point2D.LEFT, Point2D.RIGHT)
        else -> emptySet()
    }

    // map in the 3x3 expansion
    private fun Char.shape(): List<Point2D> = when (this) {
        '|' -> listOf(
            Point2D(1, 0), // _x_
            Point2D(1, 1), // _x_
            Point2D(1, 2)  // _x_
        )
        '-' -> listOf(
            // ___
            // xxx
            // ___
            Point2D(0, 1), Point2D(1, 1), Point2D(2, 1)
        )
        'L' -> listOf(
            Point2D(1, 0),                      // _x_
            Point2D(1, 1), Point2D(2, 1)   // _xx
                                                     // ___
        )
        'J' -> listOf(
            Point2D(1, 0),                      // _x_
            Point2D(0, 1), Point2D(1, 1)   // xx_
                                                     // ___
        )
        '7' -> listOf(
                                                    // ___
            Point2D(0, 1) ,Point2D(1, 1), // xx_
            Point2D(1, 2)                      // _x_
        )
        'F' -> listOf(
                                                    // ___
            Point2D(2, 1) ,Point2D(1, 1), // _xx
            Point2D(1, 2)                      // _x_
        )
        'S' -> listOf(Point2D.UP, Point2D.DOWN, Point2D.LEFT, Point2D.RIGHT, Point2D(0, 0)).map { it + Point2D(1, 1) }
        else -> listOf()
    }

    override fun part1(input: List<String>): Long {
        val map = input.indices.flatMap { y ->
            input[y].indices.map { x -> Point2D(x, y) to input[y][x] }
        }.associate { it }
        val start = map.filterValues { it == 'S' }.keys.firstOrNull() ?: error("no start position found")
        val graph = AdjacencyListGraph(map.keys.filter { map[it] != '.' }) { pos ->
            pos.cardinalNeighbors.filter { map.getOrDefault(it, '.').isConnectedTo(pos - it) }
        }
        val (distance, _) = graph.dijkstra(start) { _, _ -> 1 }
        return map.keys.maxOf { distance(it) ?: Long.MIN_VALUE }
    }

    override fun part2(input: List<String>): Any {
        val map = input.indices.flatMap { y ->
            input[y].indices.map { x -> Point2D(x, y) to input[y][x] }
        }.associate { it }
        val start = map.entries.firstOrNull { it.value == 'S' }?.key ?: error("no start position found")
        val reachableFromStart = buildSet {
            val unexplored = mutableListOf(start)
            while (unexplored.isNotEmpty()) {
                val cur = unexplored.removeFirst()
                this += cur
                map[cur]!!.connectedTo().forEach { direction ->
                    val point = direction + cur
                    if (point !in this) {
                        val pipe = map[point]
                        val reversed = direction.reversed()
                        if (pipe != null && pipe.isConnectedTo(reversed)) {
                            unexplored += point
                        }
                    }
                }
            }
        }
        val expandedMap = mutableMapOf<Point2D, Char>()
        map.forEach {
            val topLeftOf3x3 = Point2D(it.key.x * 3, it.key.y * 3)
            val centerOf3x3 = topLeftOf3x3 + Point2D.DOWN + Point2D.RIGHT
            expandedMap[centerOf3x3] = '.'
            centerOf3x3.neighborHood.forEach { p ->
                expandedMap[p] = '.'
            }
            if (it.key in reachableFromStart) {
                it.value.shape().forEach { n ->
                    expandedMap[topLeftOf3x3 + n] = '#'
                }
            }
        }
        val toFlood = mutableListOf(Point2D(0, 0))
        while (toFlood.isNotEmpty()) {
            val p = toFlood.removeFirst()
            expandedMap[p] = '='
            toFlood += p.cardinalNeighbors.filter { expandedMap[it] == '.' && it !in toFlood }
        }
        return map.count { expandedMap[Point2D(x = it.key.x * 3 + 1, y = it.key.y * 3 + 1)] == '.' }
    }
}

fun main() = Day10.run()
