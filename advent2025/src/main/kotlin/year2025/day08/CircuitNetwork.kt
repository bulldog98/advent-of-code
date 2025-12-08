package year2025.day08

import Point3D

data class CircuitNetwork(
    val junctionBoxes: Set<Point3D>
) {
    val size: Int
        get() = junctionBoxes.size

    constructor(junctionBox: Point3D) : this(setOf(junctionBox))

    operator fun contains(junctionBox: Point3D) = junctionBox in junctionBoxes
}