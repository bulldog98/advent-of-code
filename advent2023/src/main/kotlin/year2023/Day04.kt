package year2023

import AdventDay
import kotlin.math.pow

private val NUMBER = """(\d)+""".toRegex()

data class Card(val id: Int, val left: List<Int>, val right: List<Int>) {
    constructor(input: String): this(
        input.substringAfter("Game ").split(":")[0].drop(4).trim().toInt(),
        input.substringAfter(":").split("|")
    )
    constructor(id: Int, restInput: List<String>): this(
        id,
        restInput[0].chunked(3).filter { it.isNotBlank() }.map {
            it.trim().toInt()
        }.toList(),
        restInput[1].chunked(3).filter { it.isNotBlank() }.map {
            it.trim().toInt()
        }.toList(),
    )
}

object Day04 : AdventDay(2023, 4) {
    override fun part1(input: List<String>) =
        input.map(::Card)
            .sumOf {
                val winningCardsCount = it.right.count { c -> c in it.left }
                if (winningCardsCount == 0)
                    0
                else
                    2.toDouble().pow(winningCardsCount - 1).toInt()
            }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day04.run()
