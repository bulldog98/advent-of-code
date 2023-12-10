package year2023.day10

import Point2D

class PipeMapWithSqueezingBetweenPipes(
    private val baseMap: PipeMap
) {
    internal val computedMap: Map<Point2D, Char> by lazy {
        baseMap.flatMap {
            val topLeftOf3x3 = Point2D(it.key.x * 3, it.key.y * 3)
            val char = if (it.key in baseMap.reachablePoints) {
                it.value
            } else {
                '.'
            }
            char.mapTo3x3Result(topLeftOf3x3)
        }.associate { it }
    }

    fun countNumberOfTilesWithinLoop(): Int {
        val floodedMap = computedMap.toMutableMap()
        val toFlood = mutableListOf(Point2D(0, 0))
        while (toFlood.isNotEmpty()) {
            val p = toFlood.removeFirst()
            floodedMap[p] = '='
            toFlood += p.cardinalNeighbors.filter { floodedMap[it] == '.' && it !in toFlood }
        }
        return baseMap.count { floodedMap[Point2D(x = it.key.x * 3 + 1, y = it.key.y * 3 + 1)] == '.' }
    }

    companion object {
        private fun List<String>.to3x3(topLeftOffset: Point2D): List<Pair<Point2D, Char>> =
            indices.flatMap { y ->
                this[y].indices.map { x ->
                    Point2D(x + topLeftOffset.x, y + topLeftOffset.y) to this[y][x]
                }
            }

        // map in the 3x3 expansion
        private fun Char.mapTo3x3Result(topLeftOffset: Point2D): List<Pair<Point2D, Char>> = when (this) {
            '|' -> listOf(
                ".#.",
                ".#.",
                ".#."
            )
            '-' -> listOf(
                "...",
                "###",
                "..."
            )
            'L' -> listOf(
                ".#.",
                ".##",
                "..."
            )
            'J' -> listOf(
                ".#.",
                "##.",
                "..."
            )
            '7' -> listOf(
                "...",
                "##.",
                ".#."
            )
            'F' -> listOf(
                "...",
                ".##",
                ".#."
            )
            'S' -> listOf(
                ".#.",
                "###",
                ".#."
            )
            '.' -> listOf(
                "...",
                "...",
                "..."
            )
            else -> error("should not happen $this")
        }.to3x3(topLeftOffset)
    }
}
