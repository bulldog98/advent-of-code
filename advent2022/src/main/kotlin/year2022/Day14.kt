package year2022

import adventday.AdventDay

class Day14 : AdventDay(2022, 14) {
    private val startPoint = 500 to 0
    data class Area(
        val blockedByRock: Set<Pair<Int, Int>>,
        val blockedBySand: Set<Pair<Int, Int>> = emptySet()
    ) {
        companion object {
            private fun allBetween(a: Pair<Int, Int>, b: Pair<Int, Int>): Collection<Pair<Int, Int>> = when {
                a.first <= b.first && a.second <= b.second -> (a.first..b.first).flatMap {
                    (a.second..b.second).map { d -> it to d }
                }
                a.first > b.first && a.second <= b.second -> (b.first..a.first).flatMap {
                    (a.second..b.second).map { d -> it to d }
                }
                a.first <= b.first && a.second > b.second -> (a.first..b.first).flatMap {
                    (b.second..a.second).map { d -> it to d }
                }
                else -> (b.first..a.first).flatMap {
                    (b.second..a.second).map { d -> it to d }
                }
            }
            fun of(input: List<String>): Area {
                val rocks = input.fold(mutableSetOf<Pair<Int, Int>>()) { acc, cur ->
                    cur.split(" -> ").map {
                        val (a, b) = it.split(",")
                        a.toInt() to b.toInt()
                    }.windowed(2).forEach { (a, b) ->
                        acc.addAll(allBetween(a, b))
                    }
                    acc
                }
                return Area(rocks)
            }
        }

        val lowestRow: Int
            get() = listOf(
                blockedByRock.maxOfOrNull { (_, a) -> a },
                blockedBySand.maxOfOrNull { (_, a) -> a },
            ).mapNotNull { it }.max()

        private fun isEmpty(point: Pair<Int, Int>): Boolean =
            point !in blockedByRock && point !in blockedBySand

        private tailrec fun sandFallsFrom(point: Pair<Int, Int>): Pair<Int, Int>? {
            if (point.second > lowestRow) {
                return null
            }
            if (isEmpty(point.first to point.second + 1)) {
                return sandFallsFrom(point.first to point.second + 1)
            }
            if (isEmpty(point.first - 1 to point.second + 1)) {
                return sandFallsFrom(point.first - 1 to point.second + 1)
            }
            if (isEmpty(point.first + 1 to point.second + 1)) {
                return sandFallsFrom(point.first + 1 to point.second + 1)
            }
            return point
        }

        fun simulateSandFall(point: Pair<Int, Int>): Area {
            val newSand = sandFallsFrom(point) ?: return this
            return copy(blockedBySand = blockedBySand + newSand)
        }
    }

    override fun part1(input: List<String>): Int {
        var area = Area.of(input)
        while (true) {
            val next = area.simulateSandFall(startPoint)
            if (next == area) return area.blockedBySand.size
            area = next
        }
    }

    override fun part2(input: List<String>): Int {
        var area = Area.of(input)
        val leftX = area.blockedByRock.minOf { (a) -> a }
        val rightX = area.blockedByRock.maxOf { (a) -> a }
        val lowest = area.lowestRow
        val additional = (leftX - lowest..rightX + lowest).map { x -> x to lowest + 2 }
        area = area.copy(blockedByRock = area.blockedByRock + additional)
        while (true) {
            val next = area.simulateSandFall(startPoint)
            if (next == area) return area.blockedBySand.size
            area = next
        }
    }
}

fun main() = Day14().run()
