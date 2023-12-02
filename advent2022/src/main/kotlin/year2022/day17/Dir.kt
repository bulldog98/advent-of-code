package year2022.day17

enum class Dir(val coordinate: Coordinate) {
    LEFT(-1 xy 0),
    RIGHT(1 xy 0)
}

fun dirFrom(c: Char) = when (c) {
    '<' -> Dir.LEFT
    '>' -> Dir.RIGHT
    else -> error("unknown dir")
}