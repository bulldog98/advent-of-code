package year2023

import Point3D
import adventday.AdventDay
import adventday.InputRepresentation
import io.ksmt.KContext
import io.ksmt.expr.KBitVec64Value
import io.ksmt.expr.KExpr
import io.ksmt.solver.KSolverStatus
import io.ksmt.solver.z3.KZ3Solver
import io.ksmt.sort.KBvSort
import io.ksmt.utils.getValue
import io.ksmt.utils.mkConst
import kotlin.time.Duration.Companion.seconds

object Day24: AdventDay(2023, 24, "Never Tell Me The Odds") {
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

    override fun part1(input: InputRepresentation): Int {
        val pairs = input.lines.map { it: String -> HailStone.of(it) }
        val xYRange = 200000000000000.0..400000000000000.0
        return pairs.allPairs().count { (a, b) ->
            val (x, y) = a.asLine().intersect(b.asLine()) ?: (0.0 to 0.0)
            x in xYRange && y in xYRange && a.canReach(x, y) && b.canReach(x, y)
        }
    }

    override fun part2(input: InputRepresentation): Any {

        val pairs = input.lines.map { it: String -> HailStone.of(it) }
        return with(KContext()) {
            operator fun <T: KBvSort> KExpr<T>.times(other: KExpr<T>): KExpr<T> = mkBvMulExpr(this, other)
            operator fun <T: KBvSort> KExpr<T>.plus(other: KExpr<T>): KExpr<T> = mkBvAddExpr(this, other)
            operator fun <T: KBvSort> KExpr<T>.times(other: Long): KExpr<T> = times(mkBv(other, sort))
            operator fun <T: KBvSort> KExpr<T>.plus(other: Long): KExpr<T> = plus(mkBv(other, sort))

            val x by bv64Sort
            val y by bv64Sort
            val z by bv64Sort
            val vX by bv64Sort
            val vY by bv64Sort
            val vZ by bv64Sort

            fun KZ3Solver.addConstrainsFor(hailStoneNumber: Int, hailStone: HailStone) {
                val t = bv64Sort.mkConst("t${hailStoneNumber}")
                assert(mkBvSignedGreaterOrEqualExpr(t, mkBv(0L)))
                assert((x + (vX * t)) eq (t * hailStone.velocity.x) + hailStone.initialPosition.x)
                assert((y + (vY * t)) eq (t * hailStone.velocity.y) + hailStone.initialPosition.y)
                assert((z + (vZ * t)) eq (t * hailStone.velocity.z) + hailStone.initialPosition.z)
            }

            KZ3Solver(this).use { solver ->
                // for my input the first 3 were not enough
                pairs.take(4).forEachIndexed(solver::addConstrainsFor)
                solver.check(timeout = 15.seconds).let {
                    require(it == KSolverStatus.SAT) { "$it" }
                }
                (solver.model().eval(x + y + z) as KBitVec64Value).longValue
            }
        }
    }
}

fun main() = Day24.run()
