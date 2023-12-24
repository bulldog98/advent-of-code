package year2023

import AdventDay
import Point3D

object Day24: AdventDay(2023, 24) {
    data class Line(val m: Double, val c: Double) {
        operator fun invoke(x: Double): Double = x * m + c
    }
    data class HailStone(val initialPosition: Point3D, val velocity: Point3D) {
        fun asLine(): Line {
            val m = velocity.y.toDouble() / velocity.x.toDouble()
            val c = initialPosition.y.toDouble() - m * initialPosition.x.toDouble()
            return Line(
                m,
                c
            )
        }

        fun canReach(x: Double, y: Double) =
            if (velocity.x < 0) x <= initialPosition.x else x >= initialPosition.x &&
                if (velocity.y < 0) y <= initialPosition.y else y >= initialPosition.y
        companion object {
            fun of(input: String): HailStone = input.split(" @ ").let { (a, b) ->
                val (x1, y1, z1) = a.split(", ").map { it.trim() }
                val (x2, y2, z2) = b.split(", ").map { it.trim() }
                HailStone(Point3D(x1.toLong(), y1.toLong(), z1.toLong()),
                    Point3D(x2.toLong(), y2.toLong(), z2.toLong()))
            }
        }
    }

    private fun <E> List<E>.allPairs(): List<Pair<E, E>> = buildList {
        this@allPairs.forEachIndexed { i: Int, elem ->
            this@allPairs.subList(i + 1, this@allPairs.size).forEach { elem2 ->
                this += elem to elem2
            }
        }
    }

    private fun Line.intersect(other: Line): Pair<Double, Double>? {
        if (m == other.m) return null
        val x = (other.c - c) / (m - other.m)
        return x to other(x)
    }

    override fun part1(input: List<String>): Int {
        val pairs = input.map {HailStone.of(it) }
        val xYRange = 200000000000000.0..400000000000000.0
        return pairs.allPairs().count { (a, b) ->
            val (x, y) = a.asLine().intersect(b.asLine()) ?: (0.0 to 0.0)
            x in xYRange && y in xYRange && a.canReach(x, y) && b.canReach(x, y)
        }
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day24.run()
