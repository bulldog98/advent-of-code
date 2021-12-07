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

fun main() {
    fun part1(input: List<Int>): Int =
        input.indices
            .minOfOrNull { input.alignTo(it) }
            ?: throw Error("no valid config")

    fun part2(input: List<Int>): Int =
        input.indices
            .minOfOrNull { input.alignTo(it, ::increaseByStep) }
            ?: throw Error("no valid config")

    val testInput = readInput("Day07_test")[0].split(",").map(String::toInt)
    val input = readInput("Day07")[0].split(",").map(String::toInt)

    // test if implementation meets criteria from the description:
    check(part1(testInput) == 37)
    println(part1(input))

    // test if implementation meets criteria from the description:
    check(part2(testInput) == 168)
    println(part2(input))
}