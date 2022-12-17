package day17

// rows should be bottom to top and only rows with rocks #
data class Chamber(
    val rocks: Set<Coordinate> = (0  until  7).map { it xy -1 }.toSet(),
    val shapes: List<Set<Coordinate>>,
    val dirs: List<Dir>,
    val currentDirIndex: Int = 0,
    val lastShapeRestOn: Coordinate? = null
) {
    private fun currentDir(i: Int): Dir = dirs[i % dirs.size]
    operator fun get(point: Coordinate): Char = if (point in rocks) '#' else '.'

    fun simulateOneStep(stepNumber: Int): Chamber {
        val currentShape = shapes[stepNumber % shapes.size]
        val maxHeight = rocks.maxOf { it.y }
        val shiftBy = 2L xy 4 + maxHeight

        var shapeAtCorrectPosition = currentShape.map { it + shiftBy }
        var currentDir = currentDir(currentDirIndex)
        var pushedByWindXTimes = 0
        while (true) {
            //try to push with wind direction
            if (isOpen(shapeAtCorrectPosition, currentDir.coordinate)) {
                shapeAtCorrectPosition = shapeAtCorrectPosition.map { it + currentDir.coordinate }
            }
            pushedByWindXTimes += 1

            if (isOpen(shapeAtCorrectPosition, Coordinate.DOWN)) {
                shapeAtCorrectPosition = shapeAtCorrectPosition.map { it + Coordinate.DOWN }
                currentDir = currentDir(currentDirIndex + pushedByWindXTimes)
            } else {
                return copy(
                    rocks = rocks + shapeAtCorrectPosition,
                    currentDirIndex = currentDirIndex + pushedByWindXTimes,
                    lastShapeRestOn = shapeAtCorrectPosition[0]
                )
            }
        }
    }

    private fun isOpen(shape: Collection<Coordinate>, offset: Coordinate): Boolean {
        return shape.none {
            val spot = it + offset
            spot.x !in 0 until 7 || spot in rocks
        }
    }

    fun prettyPrint() {
        val maxY = rocks.maxOf { it.y }
        for (y in maxY downTo 0) {
            for (x in 0 until 7L) {
                if (x xy y in rocks) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println()
        }
    }
}