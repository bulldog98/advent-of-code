package year2023

import AdventDay
import helper.numbers.parseAllInts

object Day12 : AdventDay(2023, 12) {
    fun String.replaceFirstChar(char: Char, with: List<Char>): List<String> =
        when (firstOrNull()) {
            null -> listOf("")
            '?' -> drop(1).replaceFirstChar(char, with).flatMap { listOf(".$it", "#$it") }
            else -> drop(1).replaceFirstChar(char, with).map { "${first()}$it" }
        }

    fun String.computeNumRepresentation(cur: Int = 0, inGroup: Boolean = false): List<Int> =
        when (firstOrNull()) {
            null -> if (cur != 0) listOf(cur) else emptyList()
            '.' ->
                if (inGroup)
                    listOf(cur) + drop(1).computeNumRepresentation()
                else
                    drop(1).computeNumRepresentation()
            '#' ->
                if (inGroup)
                    drop(1).computeNumRepresentation(cur + 1, true)
                else
                    drop(1).computeNumRepresentation(1, true)
            else -> error("can not handle ${first()}")
        }

    data class Line(val charRepresentation: String, val numRepresentation: List<Int>) {
        companion object {
            fun of(input: String): Line {
                val (first, second) = input.split(" ")
                return Line(first, second.parseAllInts().toList())
            }
        }

        fun findAllPossible(): List<String> {
            return charRepresentation
                .replaceFirstChar('?', listOf('#', '.'))
                .filter { it.computeNumRepresentation() == numRepresentation }
        }
    }

    override fun part1(input: List<String>): Int =
        input
            .map(Line::of)
            .map { it.findAllPossible() }
            .sumOf { it.size }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day12.run()
