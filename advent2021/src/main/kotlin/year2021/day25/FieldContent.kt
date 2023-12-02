package year2021.day25

import Point2D

sealed interface FieldContent {
    fun move(point2D: Point2D): Point2D

    data object EastMovingSeaCucumberHerd : FieldContent {
        override fun move(point2D: Point2D): Point2D = point2D + Point2D.RIGHT
    }

    data object SouthMovingSeaCucumberHerd : FieldContent {
        override fun move(point2D: Point2D): Point2D = point2D + Point2D.DOWN
    }

    companion object {
        operator fun invoke(char: Char): FieldContent? = when (char) {
            '.' -> null
            '>' -> EastMovingSeaCucumberHerd
            'v' -> SouthMovingSeaCucumberHerd
            else -> error("should not happen")
        }
    }
}