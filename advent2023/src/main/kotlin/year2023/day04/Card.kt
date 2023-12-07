package year2023.day04

import helper.numbers.NUMBERS_REGEX
import kotlin.math.pow

// maybe use left.intersect(right) ?
data class Card(val id: Int, val left: List<Int>, val right: List<Int>) {
    constructor(input: String): this(
        input.substringAfter("Game ").split(":")[0].drop(4).trim().toInt(),
        input.substringAfter(":").split("|")
    )
    constructor(id: Int, restInput: List<String>): this(
        id,
        NUMBERS_REGEX.findAll(restInput[0]).map { it.value.toInt() }.toList(),
        NUMBERS_REGEX.findAll(restInput[1]).map { it.value.toInt() }.toList(),
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