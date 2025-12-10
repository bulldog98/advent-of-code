package year2021

import adventday.AdventDay
import adventday.InputRepresentation
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private fun addIfFirstMaxIsZero(a: Int, b: Int): Int =
    if (a == Int.MAX_VALUE)
        b
    else
        a + b

private fun Collection<Int>.alignTo(i: Int, cost: (Int) -> Int = ::abs): Int = asSequence()
    .map { max(it, i) - min(it, i) }
    .filter { cost(it) > 0 }
    .map { cost(it) }
    .fold(Int.MAX_VALUE, ::addIfFirstMaxIsZero)

fun increaseByStep(n: Int): Int = (n * (n+1)) / 2

object Day07 : AdventDay(2021, 7, "The Treachery of Whales") {
    override fun part1(input: InputRepresentation): Int = with(input.lines[0].split(",").map(String::toInt)) {
        indices.minOfOrNull {
            this.alignTo(it)
        } ?: throw Error("no valid config")
    }

    override fun part2(input: InputRepresentation): Int = with(input.lines[0].split(",").map(String::toInt)) {
        indices.minOfOrNull {
            this.alignTo(it, ::increaseByStep)
        } ?: throw Error("no valid config")
    }
}

fun main() = Day07.run()
