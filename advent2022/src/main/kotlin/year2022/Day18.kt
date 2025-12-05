package year2022

import Point3D
import adventday.AdventDay
import adventday.InputRepresentation
import exploreFrom

class Day18 : AdventDay(2022, 18) {
    enum class Plane {
        XY, YZ, XZ
    }

    // needed because you have to differentiate
    data class Face(val point: Point3D, val plane: Plane)

    private val Point3D.faces
        get() = setOf(
            Face(this, Plane.XY),
            Face(this, Plane.XZ),
            Face(this, Plane.YZ),
            Face(this + Point3D.UP, Plane.XY),
            Face(this + Point3D.RIGHT, Plane.XZ),
            Face(this + Point3D.FRONT, Plane.YZ)
        )

    data class Boundary3D(val topFrontRight: Point3D, val bottomBackLeft: Point3D) {
        operator fun contains(other: Point3D) =
            other.x in (topFrontRight.x..bottomBackLeft.x) &&
                    other.y in (topFrontRight.y..bottomBackLeft.y) &&
                    other.z in (topFrontRight.z..bottomBackLeft.z)

        companion object {
            fun from(points: Collection<Point3D>): Boundary3D {
                val topFrontRight =
                    Point3D(points.minOf { it.x } - 1, points.minOf { it.y } - 1, points.minOf { it.z } - 1)
                val bottomBackLeft =
                    Point3D(points.maxOf { it.x } + 1, points.maxOf { it.y } + 1, points.maxOf { it.z } + 1)
                return Boundary3D(topFrontRight, bottomBackLeft)
            }
        }
    }

    override fun part1(input: InputRepresentation): Int {
        val lavaDroplets = input.lines.map { it: String ->
            it.split(",").let { (x, y, z) ->
                Point3D(x.toLong(), y.toLong(), z.toLong())
            }
        }.toSet()
        return lavaDroplets.sumOf { it.neighbors.count { n -> n !in lavaDroplets } }
    }

    override fun part2(input: InputRepresentation): Int {
        val lavaDroplets = input.lines.map { it: String -> Point3D.parse(it) }
        val potentiallyExposedToAir = lavaDroplets.flatMap { it.faces }.toSet()

        val boundary = Boundary3D.from(lavaDroplets)

        val visitedFaces = mutableSetOf<Face>()
        exploreFrom(Point3D.ORIGIN) {
            if (item !in lavaDroplets) {
                visitedFaces.addAll(potentiallyExposedToAir.intersect(item.faces))
                markElementsAsToVisit(item.neighbors.filter { it in boundary })
            }
        }
        return visitedFaces.size
    }
}

fun main() = Day18().run()