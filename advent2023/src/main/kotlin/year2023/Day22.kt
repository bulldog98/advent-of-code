package year2023

import AdventDay
import Point3D
import helper.numbers.parseAllInts
import year2023.Day22.Brick.Companion.recomputeSupports

object Day22 : AdventDay(2023, 22) {
    data class Brick(val points: List<Point3D>, val supportedBy: List<Brick> = emptyList()) {
        val supported: Boolean
            get() = supportedBy.isNotEmpty() || points.first().z == 1
        fun isSupportedWithOutBricks(other: Brick): Boolean =
            points.first().z == 1 || (supportedBy - other).isNotEmpty()

        companion object {
            private fun String.parseBrickPieces(): Brick {
                val (left, right) = split("~")
                    .map {
                        it.parseAllInts()
                            .toList()
                            .let { (x, y, z) -> Point3D(x, y, z) }
                    }
                return Brick(when {
                    // input always only has one of x, y, z different
                    // coords left are always < right
                    left == right -> listOf(right)
                    left.x != right.x -> (left.x..right.x).map { x -> Point3D(x, left.y, left.z) }
                    left.y != right.y -> (left.y..right.y).map { y -> Point3D(left.x, y, left.z) }
                    left.z != right.z -> (left.z..right.z).map { z -> Point3D(left.x, left.y, z) }
                    else -> error("unknown situation")
                })
            }
            fun recomputeSupports(bricks: List<Brick>): List<Brick> =
                bricks.map {
                    it.copy(
                        supportedBy = bricks.filter { b ->
                            b != it && b.points.any { p ->
                                (p + Point3D(0, 0, 1) in it.points)
                            }
                        }
                    )
                }
            fun of(input: List<String>): List<Brick> =
                input
                    .map { it.parseBrickPieces() }
                    .let { bricks ->
                        bricks.sortedBy { it.points.first().z }
                    }.let { bricks ->
                        recomputeSupports(bricks)
                    }
        }
    }

    private fun List<Brick>.settle(): List<Brick> =
        generateSequence(this) { bricks ->
            recomputeSupports(bricks.map { brick ->
                if (brick.supported)
                    brick
                else
                    brick.copy(points = brick.points.map { it + Point3D(0, 0, -1) })
            })
        }.zipWithNext()
            .first { (a, b) -> a == b }
            .first

    override fun part1(input: List<String>): Any {
        val settledBricks = Brick.of(input).settle()
        return settledBricks.count { brick ->
            val otherBricks = settledBricks.filter { it != brick }
            otherBricks.all { it.isSupportedWithOutBricks(brick) }
        }
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day22.run()
