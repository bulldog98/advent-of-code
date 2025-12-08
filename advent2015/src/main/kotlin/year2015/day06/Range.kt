package year2015.day06

import Point2D

data class Range(val upperLeft: Point2D, val lowerRight: Point2D) {
    companion object {
        fun parse(input: String) = input.split(" through ").let { (left, right) ->
            Range(left.parsePoint2D(), right.parsePoint2D())
        }

        private fun String.parsePoint2D() = split(",").let { (x, y) ->
            Point2D(x.toLong(), y.toLong())
        }
    }
}