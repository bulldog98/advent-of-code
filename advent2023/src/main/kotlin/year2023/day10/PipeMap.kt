package year2023.day10

import Point2D
import graph.AdjacencyListGraph
import graph.Graph

data class PipeMap(
    val map: Map<Point2D, Char>,
    val start: Point2D
): Map<Point2D, Char> by map {
    fun getAsGraph(): Graph<Point2D> = AdjacencyListGraph(map.keys.filter { map[it] != '.' }) { pos ->
        pos.cardinalNeighbors.filter { map.getOrDefault(it, '.').isConnectedTo(pos - it) }
    }

    val reachablePoints by lazy {
        buildSet {
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
    }

    companion object {
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

        fun of(input: List<String>): PipeMap {
            val map = input.indices.flatMap { y ->
                input[y].indices.map { x -> Point2D(x, y) to input[y][x] }
            }.associate { it }
            val start = map.filterValues { it == 'S' }.keys.firstOrNull() ?: error("no start position found")
            return PipeMap(map, start)
        }
    }
}