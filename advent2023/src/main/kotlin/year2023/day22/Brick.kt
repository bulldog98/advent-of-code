package year2023.day22

import Point3D
import helper.numbers.toAllLongs

data class Brick(val points: List<Point3D>, val supportedBy: List<Brick> = emptyList()) {
    val supported: Boolean
        get() = supportedBy.isNotEmpty() || points.first().z == 1L

    fun isSupportedWithOutBricks(other: Brick): Boolean =
        points.first().z == 1L || (supportedBy - other).isNotEmpty()

    companion object {
        private val down = Point3D(0, 0, -1)
        private val up = Point3D(0, 0, 1)
        private fun String.parseBrickPieces(): Brick {
            val (left, right) = split("~")
                .map {
                    it.toAllLongs()
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

        private fun recomputeSupports(bricks: List<Brick>): List<Brick> =
            bricks.map {
                it.copy(
                    supportedBy = bricks.filter { b ->
                        b != it && b.points.any { p ->
                            (p + up in it.points)
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

        fun List<Brick>.settle(): List<Brick> =
            generateSequence(this) { bricks ->
                recomputeSupports(bricks.map { brick ->
                    if (brick.supported)
                        brick
                    else
                        brick.copy(points = brick.points.map { it + down })
                })
            }.zipWithNext()
                .first { (a, b) -> a == b }
                .first
    }
}
