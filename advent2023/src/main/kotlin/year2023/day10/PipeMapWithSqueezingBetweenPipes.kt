package year2023.day10

import Point2D

class PipeMapWithSqueezingBetweenPipes(
    private val baseMap: PipeMap
) {
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

    private val computedMap: Map<Point2D, Char> by lazy {
        buildMap {
            baseMap.forEach {
                val topLeftOf3x3 = Point2D(it.key.x * 3, it.key.y * 3)
                val centerOf3x3 = topLeftOf3x3 + Point2D.DOWN + Point2D.RIGHT
                this[centerOf3x3] = '.'
                centerOf3x3.neighborHood.forEach { p ->
                    this[p] = '.'
                }
                if (it.key in baseMap.reachablePoints) {
                    it.value.shape().forEach { n ->
                        this[topLeftOf3x3 + n] = '#'
                    }
                }
            }
        }
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
}
