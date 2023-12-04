package year2021.day15

import Point2D

data class Field(
    val topLeft: Point2D,
    val bottomRight: Point2D,
    private val input: List<String>
) : Map<Point2D, Int> {
    constructor(bottomRight: Point2D, input: List<String>) : this(Point2D(0, 0), bottomRight, input)

    val xRange by lazy {
        (topLeft.x..bottomRight.x)
    }
    val yRange by lazy {
        (topLeft.y..bottomRight.y)
    }
    private data class Entry(override val key: Point2D, override val value: Int): Map.Entry<Point2D, Int>

    operator fun contains(point: Point2D): Boolean =
        point.x in xRange && point.y in yRange

    private val points: Set<Point2D>
        get() = xRange.flatMap { x ->
            yRange.map { y ->
                Point2D(x, y)
            }
        }.toSet()

    fun connectionFrom(point: Point2D): List<Point2D> =
        point.cardinalNeighbors.filter {
            it in this
        }

    override val entries: Set<Map.Entry<Point2D, Int>>
        get() = keys.map { Entry(it, this[it] ?: error("point is not in field")) }.toSet()
    override val keys: Set<Point2D>
        get() = points
    override val size: Int
        get() = bottomRight.x.toInt() * bottomRight.y.toInt()
    override val values: Collection<Int>
        get() = keys.map { this[it] ?: error("point is not in field") }

    override fun isEmpty(): Boolean = false

    override fun get(key: Point2D): Int? =
        if (key in this) input[key.y.toInt()][key.x.toInt()].digitToInt() else null

    override fun containsValue(value: Int): Boolean =
        value in (1..9) && input.any { it.contains(value.toString()[0]) }

    override fun containsKey(key: Point2D): Boolean =
        key in this

    companion object {
        fun of(input: List<String>): Field = Field(
            Point2D(input[0].length - 1, input.size - 1),
            input
        )
    }
}