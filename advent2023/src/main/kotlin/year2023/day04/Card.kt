package year2023.day04

import kotlin.math.pow

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

    val winningNumbersCount by lazy {
        right.count { c -> c in left }
    }

    val score by lazy {
        if (winningNumbersCount == 0)
            0
        else
            2.toDouble().pow(winningNumbersCount - 1).toInt()
    }
}