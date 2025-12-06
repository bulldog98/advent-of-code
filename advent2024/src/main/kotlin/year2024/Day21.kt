package year2024

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import helper.numbers.toAllLongs

object Day21 : AdventDay(2024, 21, "Keypad Conundrum") {
    /**
     * give a multiline string, - for holes
     */
    private data class KeyPad(
        val stringRepresentation: String
    ) {
        private val map = buildMap {
            stringRepresentation.lines().forEachIndexed { y, line ->
                line.forEachIndexed { x, c ->
                    put(c, Point2D(x, y))
                }
            }
        }

        fun getPositionOf(char: Char) = map.getValue(char)
    }

    private val numberPad = KeyPad(
        """
        789
        456
        123
        -0A
    """.trimIndent()
    )

    private val directionPad = KeyPad(
        """
        -^A
        <v>
    """.trimIndent()
    )

    private fun List<String>.computeInputComplexity(robotDepthCount: Int) =
        sumOf { it.lengthOfCommand(robotDepthCount) * it.toAllLongs().first() }

    private fun String.lengthOfCommand(depth: Int, first: Boolean = true): Long = "A$this"
        .zipWithNext { a, b -> State(a, b, depth).computeLengthOfInput(first) }
        .sum()

    data class State(val from: Char, val to: Char, val depth: Int)

    private val memoization = mutableMapOf<State, Long>()
    private fun State.computeLengthOfInput(isNumberPad: Boolean): Long = memoization.getOrPut(this) {
        val keypad = if (isNumberPad) numberPad else directionPad
        val routes = findPathsOn(keypad)
        when (depth) {
            0 -> routes.first().length.toLong()
            else -> routes.minOf { it.lengthOfCommand(depth - 1, false) }
        }
    }

    private fun State.findPathsOn(keypad: KeyPad): List<String> {
        val start = keypad.getPositionOf(from)
        val destination = keypad.getPositionOf(to)
        val hole = keypad.getPositionOf('-')

        return generateSequence(listOf(start to emptyList<Point2D>())) { frontier ->
            frontier.flatMap { (currentPosition, path) ->
                when (currentPosition) {
                    hole -> emptyList()
                    else -> buildList {
                        if (currentPosition.x > destination.x)
                            this += currentPosition + Point2D.LEFT to path + Point2D.LEFT
                        if (currentPosition.x < destination.x)
                            this += currentPosition + Point2D.RIGHT to path + Point2D.RIGHT
                        if (currentPosition.y > destination.y)
                            this += currentPosition + Point2D.UP to path + Point2D.UP
                        if (currentPosition.y < destination.y)
                            this += currentPosition + Point2D.DOWN to path + Point2D.DOWN
                    }
                }
            }
        }.takeWhile { it.isNotEmpty() }.last().map { (_, line) -> line.asInstructionString() }
    }

    private fun List<Point2D>.asInstructionString() = map {
        when (it) {
            Point2D.UP -> '^'
            Point2D.DOWN -> 'v'
            Point2D.RIGHT -> '>'
            Point2D.LEFT -> '<'
            else -> error("invalid direction")
        }
    }.joinToString("", postfix = "A")

    override fun part1(input: InputRepresentation): Long = input.lines.computeInputComplexity(2)

    override fun part2(input: InputRepresentation): Long = input.lines.computeInputComplexity(25)
}

fun main() = Day21.run()