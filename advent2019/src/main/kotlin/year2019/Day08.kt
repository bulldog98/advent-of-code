package year2019

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation

class Day08(
    private val width: Int,
    private val height: Int
) : AdventDay(2019, 8) {
    private data class Layer(val rows: List<List<Int>>) {
        fun countOfDigits(digit: Int) = rows.sumOf { row ->
            row.count { it == digit }
        }

        operator fun get(point: Point2D) = rows[point.y.toInt()][point.x.toInt()]

        companion object {
            private val digitRegex = """\d""".toRegex()
            fun parseInput(input: InputRepresentation, width: Int, height: Int): List<Layer> =
                digitRegex.findAll(input.asText()).map { it.value.toInt() }.chunked(width * height) {
                    Layer(it.chunked(width))
                }.toList()
        }
    }

    private operator fun List<Layer>.get(point: Point2D) = fold(2) { currentPixel, nextLayer ->
        when (currentPixel) {
            2 -> nextLayer[point]
            else -> currentPixel
        }
    }

    override fun part1(input: InputRepresentation): Int {
        val layers = Layer.parseInput(input, width, height)

        val layerWithFewest0s = layers.minBy { it.countOfDigits(0) }
        return layerWithFewest0s.countOfDigits(1) * layerWithFewest0s.countOfDigits(2)
    }

    override fun part2(input: InputRepresentation): String {
        val layers = Layer.parseInput(input, width, height)

        val result = (0..<height).joinToString("\n") { y ->
            (0..<width).joinToString("") { x ->
                layers[Point2D(x, y)].toString()
            }
        }

        // have to squint at the output to see the chars that they represent
        return result
    }
}

fun main() = Day08(25, 6).run()