package year2025.day0

import Point2D

data class TachyonManifold(
    val splitters: Set<Point2D>
) {
    private val maxHeight by lazy {
        splitters.maxOf { it.y } + 1
    }
    operator fun contains(point: Point2D) = point.y <= maxHeight

    fun splitsAt(point: Point2D) = point in splitters

    fun simulateFrom(start: Point2D, onSplitterHit: (Point2D) -> Unit = {}) = generateSequence(mapOf(start to 1L)) { lastPositions ->
        if (lastPositions.keys.first() !in this@TachyonManifold) return@generateSequence null

        buildMap {
            lastPositions.forEach { (lastPosition, count) ->
                val next = lastPosition + Point2D.DOWN

                if (this@TachyonManifold.splitsAt(next)) {
                    onSplitterHit(next)
                    putIfAbsent(next + Point2D.LEFT, 0L)
                    putIfAbsent(next + Point2D.RIGHT, 0L)
                    this.computeIfPresent(next + Point2D.LEFT) { _, it ->
                        it + count
                    }
                    this.computeIfPresent(next + Point2D.RIGHT) { _, it ->
                        it + count
                    }
                } else {
                    putIfAbsent(next, 0L)
                    this.computeIfPresent(next) { _, it ->
                        it + count
                    }
                }
            }
        }
    }.last()
}