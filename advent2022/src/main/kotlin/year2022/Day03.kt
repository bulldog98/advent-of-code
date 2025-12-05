package year2022

import adventday.AdventDay
import adventday.InputRepresentation

typealias Bucket = Set<Char>

val Char.priority
    get() = when {
        this.isLowerCase() -> this - 'a' + 1
        this.isUpperCase() -> this - 'A' + 1 + 26
        else -> error("check input")
    }

private fun <E> Collection<Set<E>>.intersectAll(): Set<E> =
    reduce { a, b -> a intersect b.toSet() }

private fun splitToCompartments(input: String): List<Bucket> =
    listOf(input.take(input.length / 2).toSet(), input.drop(input.length / 2).toSet())

class Day03 : AdventDay(2022, 3) {
    override fun part1(input: InputRepresentation) = input.lines
        .sumOf {
            splitToCompartments(it)
                .intersectAll().first().priority
        }

    override fun part2(input: InputRepresentation) = input.lines
        .chunked(3)
        .map { it.map { i -> i.toSet() } }
        .sumOf {
            it[0].intersect(it[1]).intersect(it[2]).first().priority
        }
}

fun main() = Day03().run()