data class Point3D(val x: Long, val y: Long, val z: Long) {
    operator fun plus(other: Point3D) = Point3D(x + other.x, y + other.y, z + other.z)
    val neighbors: Set<Point3D>
        get() = setOf(this + UP, this + DOWN, this + LEFT, this + RIGHT, this + BACK, this + FRONT)

    @Suppress("unused")
    fun move(dx: Int = 0, dy: Int = 0, dz: Int = 0) = copy(x = x + dx, y = y + dy, z = z + dz)
    operator fun minus(other: Point3D): Point3D =
        Point3D(x - other.x, y - other.y, z - other.z)

    operator fun times(factor: Int) = Point3D(factor * x, factor * y, factor * z)

    companion object {
        val UP = Point3D(0, 0, 1)
        val DOWN = Point3D(0, 0, -1)
        val LEFT = Point3D(0, -1, 0)
        val RIGHT = Point3D(0, 1, 0)
        val BACK = Point3D(-1, 0, 0)
        val FRONT = Point3D(1, 0, 0)
        val ORIGIN = Point3D(0, 0, 0)

        fun parse(input: String) = input.split(",").let { (x, y, z) ->
            Point3D(x.toLong(), y.toLong(), z.toLong())
        }
    }
}