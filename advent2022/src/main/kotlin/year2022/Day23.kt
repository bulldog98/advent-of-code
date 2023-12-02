package year2022

import AdventDay
import Point2D
import findAllPositionsOf
import year2022.Day23.Direction.*

typealias PointComputation = Point2D.() -> Set<Point2D>

class Day23 : AdventDay(2022, 23) {
    private val Point2D.allNeighbors: Set<Point2D>
        get() = entries.map { this + it.pointDiff }.toSet()

    private val northNeighbor: PointComputation
        get() = { listOf(N, NE, NW).map { it.pointDiff + this }.toSet() }
    private val southNeighbor: PointComputation
        get() = { listOf(S, SE, SW).map { it.pointDiff + this }.toSet() }
    private val westNeighbor: PointComputation
        get() = { listOf(W, SW, NW).map { it.pointDiff + this }.toSet() }
    private val eastNeighbor: PointComputation
        get() = { listOf(E, SE, NE).map { it.pointDiff + this }.toSet() }

    private fun checkInRound(round: Int): List<Pair<PointComputation, Direction>> = when (round % 4) {
        0 -> listOf(northNeighbor to N, southNeighbor to S, westNeighbor to W, eastNeighbor to E)
        1 -> listOf(southNeighbor to S, westNeighbor to W, eastNeighbor to E, northNeighbor to N)
        2 -> listOf(westNeighbor to W, eastNeighbor to E, northNeighbor to N, southNeighbor to S)
        3 -> listOf(eastNeighbor to E, northNeighbor to N, southNeighbor to S, westNeighbor to W)
        else -> error("only 0..3 can be computed mod 4")
    }

    private fun Point2D.proposeMove(allElfs: Set<Point2D>, round: Int): Point2D? = when {
        (allNeighbors intersect allElfs).isEmpty() -> null
        else -> checkInRound(round)
            .firstOrNull { (check, _) -> (this.check() intersect allElfs).isEmpty() }
            ?.let { (_, dir) -> this + dir.pointDiff }
    }
    enum class Direction(val pointDiff: Point2D) {
        N(Point2D(0, -1)),
        NE(Point2D(1, -1)),
        NW(Point2D(-1, -1)),
        S(Point2D(0, 1)),
        SE(Point2D(1, 1)),
        SW(Point2D(-1, 1)),
        W(Point2D(-1, 0)),
        E(Point2D(1, 0))
    }

    private fun Set<Point2D>.simulateForXRounds(x: Int, startRoundNumber: Int = 0): Set<Point2D> {
        var current = this
        repeat(x) { round ->
            val proposed = current.map { it.proposeMove(current, round + startRoundNumber) }
            val moves = current.zip(proposed)
            current = buildSet {
                moves.forEach { (orig, next) ->
                    add(when {
                        next == null -> orig
                        proposed.count { it == next } == 1 -> next
                        else -> orig
                    })
                }
            }
        }
        return current
    }

    override fun part1(input: List<String>): Long {
        val elfsAfter10Rounds = input.findAllPositionsOf().simulateForXRounds(10)
        val minX = elfsAfter10Rounds.minOf { it.x }
        val minY = elfsAfter10Rounds.minOf { it.y }
        val maxX = elfsAfter10Rounds.maxOf { it.x }
        val maxY = elfsAfter10Rounds.maxOf { it.y }
        val lengthX = maxX - minX + 1
        val lengthY = maxY - minY + 1
        return (lengthX * lengthY) - elfsAfter10Rounds.size
    }

    override fun part2(input: List<String>): Int {
        var curRound = 1
        var prev = input.findAllPositionsOf()
        var curr = prev.simulateForXRounds(1, 0)
        while (prev != curr) {
            prev = curr
            curr = prev.simulateForXRounds(1, curRound)
            curRound += 1
        }
        return curRound
    }
}

fun main() = Day23().run()