package day11

import kotlin.collections.ArrayDeque

private typealias Coordinate = Pair<Int, Int>
private typealias Board = Map<Coordinate, Int>

private operator fun Coordinate.plus(other: Coordinate): Coordinate =
    first + other.first to second + other.second

private val directions = listOf(
    -1 to -1, 0 to -1, 1 to -1,
    -1 to  0,          1 to  0,
    -1 to  1, 0 to  1, 1 to  1,
)

private val Board.maxX: Int
    get() = keys.maxOf { it.first }

private val Board.maxY: Int
    get() = keys.maxOf { it.second }

val Board.flashCount: Int
    get() = entries.count { it.value == 0 }

private val Coordinate.surrounding: Collection<Coordinate>
    get() = directions
        .map { this + it }
        .filter { it.first >= 0 && it.second >= 0 }

fun List<String>.parseBoard(): Board =
    flatMapIndexed { y, row ->
        row.mapIndexed { x, char ->
            x to y to char.digitToInt()
        }
    }.associate { it }

fun Board.computeStep(): Board {
    val flashed = mutableSetOf<Coordinate>()
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
                    .filter { it.first <= maxX && it.second <= maxY }
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