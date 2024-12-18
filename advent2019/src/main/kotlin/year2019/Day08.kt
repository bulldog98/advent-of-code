package year2019

import adventday.AdventDay
import adventday.InputRepresentation

class Day08(val width: Int, val height: Int): AdventDay(2019, 8) {
    val digitRegex = """\d""".toRegex()

    override fun part1(input: InputRepresentation): Int {
        digitRegex.findAll(input.asText()).map {
            it.value.toInt()
        }
        val layers = """\d""".toRegex().findAll(input.asText()).map { it.value.toInt() }.chunked(width * height) {
            it.chunked(width)
        }.toList()

        val layerWithFewest0s = layers.minBy { it.sumOf { it.count { it == 0 } } }
        return layerWithFewest0s.sumOf { it.count { it == 1 } } * layerWithFewest0s.sumOf { it.count { it == 2 } }
    }

    override fun part2(input: InputRepresentation): Long {
        TODO("Not yet implemented")
    }
}

fun main() = Day08(25, 6).run()