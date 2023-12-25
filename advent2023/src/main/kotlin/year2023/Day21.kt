package year2023

import AdventDay
import Point2D
import findAllPositionsOf
import java.lang.Math.floorMod

// part 2 is adapted from https://github.com/mdekaste/AdventOfCode2023/blob/master/src/main/kotlin/day21/Day21.kt
object Day21 : AdventDay(2023, 21) {
    private fun Point2D.pq(height: Long, width: Long) = Point2D(floorMod(x, height), floorMod(y, width))

    data class State(
        val previousScore: Long = 0L,
        val previousFrontier: Set<Point2D> = emptySet(),
        val currentFrontier: Set<Point2D>,
        val score: Long = 1L,
        val width: Long,
        val height: Long
    ) {
        val memoryState by lazy { currentFrontier.map { it.pq(width, height) }.toSet() }
    }

    data class StateSequence(
        val width: Long,
        val height: Long,
        val map: Map<Point2D, Char>
    ) {
        private val states = generateSequence(
            State(
                currentFrontier = setOf(map.filterValues { it == 'S' }.keys.single()),
                width = width,
                height = height
            )
        ) { state ->
            val newFrontier = mutableSetOf<Point2D>()
            for (point in state.currentFrontier) {
                for (neighbour in point.cardinalNeighbors) {
                    val puPoint = neighbour.pq(state.height, state.width)
                    if (map[puPoint] != '#' && neighbour !in state.previousFrontier) {
                        newFrontier.add(neighbour)
                    }
                }
            }
            State(
                state.score,
                state.currentFrontier,
                newFrontier,
                state.previousScore + newFrontier.size,
                height,
                width
            )
        }

        fun solveGeneric(depth: Int): Long {
            val memory = mutableMapOf<Set<Point2D>, MutableList<Long>>().withDefault { mutableListOf() }
            for (state in states) {
                when (memory[state.memoryState]?.size) {
                    3 -> break
                    else -> memory[state.memoryState] = memory.getValue(state.memoryState).apply { add(state.score) }
                }
            }

            val answers = memory.values.toList()
            val indexOfCycles = answers.indexOfFirst { it.size >= 3 }
            val justValues = answers.subList(0, indexOfCycles).map { it.first() }

            return when (depth) {
                in 0..<indexOfCycles -> justValues[depth]
                else -> {
                    val formulas =
                        answers.subList(indexOfCycles, answers.size).map { (y1, y2, y3) -> toQuadratic(y1, y2, y3) }
                    val sizeOfCycle = formulas.size
                    val iteration = (depth - indexOfCycles) / sizeOfCycle + 1
                    val indexInsideCycle = (depth - indexOfCycles) % sizeOfCycle
                    formulas[indexInsideCycle].let { (a, b, c) -> a * iteration * iteration + b * iteration + c }
                }
            }
        }

        companion object {
            private fun toQuadratic(y1: Long, y2: Long, y3: Long): Triple<Long, Long, Long> {
                val a = ((y3 - y2) - (y2 - y1)) / 2
                val b = (y2 - y1) - 3 * a
                val c = y1 - (a + b)
                return Triple(a, b, c)
            }

            fun of(input: List<String>): StateSequence {
                val parsed = input.flatMapIndexed { y, row -> row.mapIndexed { x, c -> Point2D(x, y) to c } }.toMap()
                val height = parsed.maxOf { it.key.x } + 1
                val width = parsed.maxOf { it.key.y } + 1
                return StateSequence(width, height, parsed)
            }
        }
    }

    override fun part1(input: List<String>): Int {
        val start = input.findAllPositionsOf('S').single()
        val nodes = input.findAllPositionsOf('.') + start
        val possiblePositions = generateSequence(setOf(start)) {
            it.flatMap { n -> n.cardinalNeighbors.filter { neighbor -> neighbor in nodes } }.toSet()
        }
        return possiblePositions.drop(1).take(64).last().size
    }

    override fun part2(input: List<String>) =
        StateSequence
            .of(input)
            .solveGeneric(26501365)
}

fun main() = Day21.run()
