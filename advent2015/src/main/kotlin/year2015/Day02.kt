package year2015

import adventday.AdventDay
import adventday.InputRepresentation

data class PackageDimension(
    val length: Int,
    val width: Int,
    val height: Int
) {
    val surfaceArea: Int
        get() = 2*length*width + 2*width*height + 2*height*length

    val slack: Int
        get() = listOf(length, width, height).sorted().take(2).fold(1, Int::times)

    val ribbonSize: Int
        get() = listOf(length, width, height).sorted().take(2).fold(0, Int::plus) * 2 +
            length * width * height

    companion object {
        fun parse(input: String) = input.split("x").let { (l, w, h) ->
            PackageDimension(
                l.toInt(),
                w.toInt(),
                h.toInt()
            )
        }
    }
}

object Day02 : AdventDay(2015, 2, "I Was Told There Would Be No Math") {
    override fun part1(input: InputRepresentation): Long = input
        .lines
        .map { PackageDimension.parse(it) }
        .sumOf { it.surfaceArea.toLong() + it.slack }

    override fun part2(input: InputRepresentation) = input
        .lines
        .map { PackageDimension.parse(it) }
        .sumOf { it.ribbonSize.toLong() }
}

fun main() = Day02.run()
