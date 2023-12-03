package year2023.day03

import Point2D

sealed interface FieldContent
data class FieldContentNumber(val num: Int, val positions: List<Point2D>): FieldContent
data class FieldContentSymbol(val symbol: Char): FieldContent