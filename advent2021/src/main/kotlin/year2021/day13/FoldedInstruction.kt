package year2021.day13

import Point2D

data class FoldedInstruction(
    val points: Set<Point2D>
) {
    private val maxX by lazy { points.maxOf { it.x } }
    private val maxY by lazy { points.maxOf { it.y } }

    fun fancyPrint() = (0..maxX).joinToString("\n") { x ->
        (0..maxY).joinToString("") { y ->
            if (Point2D(x, y) in points)
                "#"
            else
                "."
        }
    }
}
