package year2021.day05

import kotlin.math.max

val Collection<Line>.maxX
    get() = maxOf { max(it.from.x, it.to.x) }
val Collection<Line>.maxY
    get() = maxOf { max(it.from.y, it.to.y) }

class Board(private val lines: Collection<Line>) {
    private val map: Map<Point, Int> = buildMap {
        lines.forEach { line ->
            line.points.forEach { p ->
                val r = get(p) ?: 0
                set(p, r + 1)
            }
        }
    }
    private val maxX
        get() = lines.maxX
    private val maxY
        get() = lines.maxY

    /**
     * used to generate the visual representation
     */
    fun printBoard() {
        (0..maxY).map { y -> (0..maxX).map { Point(it, y) } }.forEach { line ->
            println(line.map { p -> map[p] ?: '.' }.joinToString("\t"))
        }
    }

    fun count(test: (Pair<Point, Int>) -> Boolean): Int = map.entries.count { test(it.key to it.value) }
}