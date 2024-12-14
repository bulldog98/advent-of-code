package year2021.day11

import Point2D

private typealias Board = Map<Point2D, Int>

private val directions = Point2D.ORIGIN.neighborHood

private val Board.maxX: Long
    get() = keys.maxOf { it.x }

private val Board.maxY: Long
    get() = keys.maxOf { it.y }

val Board.flashCount: Int
    get() = entries.count { it.value == 0 }

private val Point2D.surrounding: Collection<Point2D>
    get() = directions
        .map { this + it }
        .filter { it.x >= 0 && it.y >= 0 }

fun Board.computeStep(): Board {
    val flashed = mutableSetOf<Point2D>()
    val workList = ArrayDeque(keys)
    val next = toMutableMap()
    while (workList.isNotEmpty()) {
        val coord = workList.removeFirst()
        if (coord in flashed) continue
        val oldPower = next[coord] ?: 0
        if (oldPower == 9) {
            next[coord] = 0
            flashed.add(coord)
            workList.addAll(
                coord.surrounding
                    .filter { it.x <= maxX && it.y <= maxY }
            )
        } else
            next[coord] = next[coord]!! + 1
    }
    return next
}

fun Board.simulateAllSteps(): Sequence<Board> = sequence {
    var last = this@simulateAllSteps
    while (true) {
        last = last.computeStep()
        yield(last)
    }
}