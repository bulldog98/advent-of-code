package year2023

import AdventDay

object Day15: AdventDay(2023, 15) {
    fun String.hashWithAppendix1A(): Int {
        var res = 0
        forEach { c ->
            res = ((res + c.code) * 17) % 256
        }
        return res
    }
    override fun part1(input: List<String>): Int {
        return input[0].split(",").sumOf { it.hashWithAppendix1A() }
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day15.run()
