package year2024

import AdventDay

fun List<Long>.allIncreasingMax3() = windowed(2).fold(true) { acc, (a, b) ->
    acc && a < b && b - a <= 3
}

fun List<Long>.allDecreasingMax3() = windowed(2).fold(true) { acc, (a, b) ->
    acc && b < a && a - b <= 3
}

object Day02 : AdventDay(2024, 2) {
    override fun part1(input: List<String>): Int {
        val lines = input.map { it.split(" ").map(String::toLong) }
        return lines.count { nums ->
            if (nums[0] < nums[1]) {
                nums.allIncreasingMax3()
            } else {
                nums.allDecreasingMax3()
            }
        }
    }

    override fun part2(input: List<String>): Long {
        TODO("Not yet implemented")
    }
}

fun main() = Day02.run()