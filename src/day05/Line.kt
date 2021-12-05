package day05

import kotlin.math.max
import kotlin.math.min

sealed class Line(
    val from: Point,
    val to: Point
) {
    constructor(string: String) : this(Point(string.split("->")[0]), Point(string.split("->")[1]))
    abstract val points: Set<Point>

    protected val minX
        get() = min(from.x, to.x)
    protected val maxX
        get() = max(from.x, to.x)
    protected val minY
        get() = min(from.y, to.y)
    protected val maxY
        get() = max(from.y, to.y)

    operator fun contains(p: Point) = p in points
}

open class SimpleLine(string: String): Line(string) {
    override val points: Set<Point>
        get() = when {
            from.x == to.x -> buildSet<Point>(maxY - minY) {
                addAll((minY..maxY).map { y ->
                    Point(to.x, y)
                })
            }
            from.y == to.y -> buildSet<Point>(maxX - minX) {
                addAll((minX..maxX).map { x ->
                    Point(x, to.y)
                })
            }
            else -> emptySet()
        }
}

class ComplexLine(
    string: String
) : SimpleLine(string) {
    override val points: Set<Point>
        get() =
            if (from.x != to.x && from.y != to.y)
                buildSet<Point>(maxX - minX) {
                    val leftEdge = listOf(to, from).minByOrNull { it.x } ?: throw Error("no left edge found")
                    val rightEdge = listOf(to, from).first { it != leftEdge }
                    val factor = if (leftEdge.y - rightEdge.y >= 0) -1 else 1
                    val incFunc = { steps: Int ->
                        leftEdge.y + factor * steps
                    }
                    addAll((minX..maxX).map { x -> Point(x, incFunc(x - minX)) })
                }
            else
                super.points
}
