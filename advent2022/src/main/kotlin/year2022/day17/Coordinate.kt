package year2022.day17

data class Coordinate(val x: Long, val y: Long) {
    operator fun plus(other: Coordinate) = x + other.x xy y + other.y
    companion object {
        val DOWN = Coordinate(0, -1)
    }
}

infix fun Int.xy(y: Int) = Coordinate(this.toLong(), y.toLong())
infix fun Long.xy(y: Long) = Coordinate(this, y)