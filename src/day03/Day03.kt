package day03

import readInput

typealias Bucket = Set<Char>

val Char.priority
    get() = when {
        this.isLowerCase() -> this - 'a' + 1
        this.isUpperCase() -> this - 'A' + 1 + 26
        else -> error("check input")
    }

private fun <E> Collection<Collection<E>>.intersectAll(): Set<E> =
    map { it.toSet() }.fold(setOf()) { a, b -> a.intersect(b) }

private fun splitToCompartments(input: String): List<Bucket> =
    listOf(input.take(input.length / 2).toSet(), input.drop(input.length / 2).toSet())

fun main() {
    fun part1(input: List<String>): Int = input.sumOf {
        splitToCompartments(it)
            .intersectAll().first().priority
    }
    fun part2(input: List<String>): Int = input.chunked(3).sumOf {
        it.map { i -> i.toSet() }
            .intersectAll().first().priority
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day03/Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("day03/Day03")
    println(part1(input))
    println(part2(input))
}