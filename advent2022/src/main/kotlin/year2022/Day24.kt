package year2022

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import lcm

class Day24 : AdventDay(2022, 24) {
    private data class Blizzard(val location: Point2D, val movement: Point2D) {
        fun next(boundary: Point2D): Blizzard {
            val nextLocation = location + movement
            val correctedNextLocation = when {
                nextLocation.x == 0L -> Point2D(boundary.x, location.y)
                nextLocation.x > boundary.x -> Point2D(1, location.y)
                nextLocation.y == 0L -> Point2D(location.x, boundary.y)
                nextLocation.y > boundary.y -> Point2D(location.x, 1)
                else -> nextLocation
            }
            return copy(location = correctedNextLocation)
        }
    }
    private data class MapState(val boundary: Point2D, val blizzards: Set<Blizzard>) {
        private val unsafeSpots by lazy {
            blizzards.map { it.location }.toSet()
        }

        fun isSafe(place: Point2D): Boolean =
            place !in unsafeSpots

        fun inBounds(place: Point2D): Boolean =
            place.x > 0 && place.y > 0 && place.x <= boundary.x && place.y <= boundary.y

        fun nextState(): MapState =
            copy(blizzards = blizzards.map { it.next(boundary) }.toSet())


        companion object {
            fun of(input: List<String>): MapState =
                MapState(
                    Point2D(input.first().lastIndex - 1L, input.lastIndex - 1L),
                    input.flatMapIndexed { y, row ->
                        row.mapIndexedNotNull { x, char ->
                            when (char) {
                                '>' -> Blizzard(Point2D(x, y), Point2D(1, 0))
                                '<' -> Blizzard(Point2D(x, y), Point2D(-1, 0))
                                'v' -> Blizzard(Point2D(x, y), Point2D(0, 1))
                                '^' -> Blizzard(Point2D(x, y), Point2D(0, -1))
                                else -> null
                            }
                        }
                    }.toSet()
                )
        }
    }

    private data class PathAttempt(val steps: Int, val location: Point2D) {
        fun next(place: Point2D = location): PathAttempt =
            PathAttempt(steps + 1, place)
    }

    private fun MapState.computeLookup(startsAtRound: Int = 0): (Int) -> MapState {
        val cycleLength = lcm(boundary.x, boundary.y).toInt()
        val lookupTable = (1 ..  cycleLength)
            .runningFold(startsAtRound % cycleLength to this) { (_, lastState), cur ->
                (cur + startsAtRound) % cycleLength to lastState.nextState()
            }.toMap()
        return { lookupTable.getValue(it % cycleLength) }
    }

    private fun solve(
        startPlace: Point2D,
        stopPlace: Point2D,
        startState: MapState,
        stepsSoFar: Int = 0,
        lookupMapState: (Int) -> MapState = startState.computeLookup(stepsSoFar),
    ): Pair<Int, MapState> {
        val queue = mutableListOf(PathAttempt(stepsSoFar, startPlace))
        val seen = mutableSetOf<PathAttempt>()
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            if (current !in seen) {
                seen += current
                val nextMapState = lookupMapState(current.steps + 1)
                if (nextMapState.isSafe(current.location)) {
                    queue += current.next()
                }
                val neighbors = current.location.cardinalNeighbors

                // Found the goal?
                if (stopPlace in neighbors) return Pair(current.steps + 1, nextMapState)

                queue += neighbors
                    .filter {
                        it == startPlace || (nextMapState.inBounds(it) && nextMapState.isSafe(it))
                    }
                    .map { current.next(it) }
            }
        }
        error("no path found")
    }

    override fun part1(input: InputRepresentation): Int {
        val initialMapState: MapState = MapState.of(input)
        val start = Point2D(input.first().indexOfFirst { it == '.' }, 0)
        val goal = Point2D(input.last().indexOfFirst { it == '.' }, input.lastIndex)

        return solve(start, goal, initialMapState).first
    }
    override fun part2(input: InputRepresentation): Int {
        val initialMapState: MapState = MapState.of(input)
        val start = Point2D(input.first().indexOfFirst { it == '.' }, 0)
        val goal = Point2D(input.last().indexOfFirst { it == '.' }, input.lastIndex)

        val stuff = listOf(start, goal, start, goal).zipWithNext()
        return stuff.fold(0 to initialMapState) { (rounds, state), (start, goal) ->
            solve(start, goal, state, rounds)
        }.first
    }
}

fun main() = Day24().run()