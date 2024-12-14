package year2024

import adventday.AdventDay
import adventday.InputRepresentation

fun List<Long>.allIncreasingMax3() = windowed(2).fold(true) { acc, (a, b) ->
    acc && a < b && b - a <= 3
}

fun List<Long>.allDecreasingMax3() = windowed(2).fold(true) { acc, (a, b) ->
    acc && b < a && a - b <= 3
}

fun List<Long>.omit(index: Int): List<Long> = subList(0, index) + subList(index + 1, size)

object Day02 : AdventDay(2024, 2) {
    override fun part1(input: InputRepresentation): Int {
        val lines = input.map { it.split(" ").map(String::toLong) }
        return lines.count { nums ->
            if (nums[0] < nums[1]) {
                nums.allIncreasingMax3()
            } else {
                nums.allDecreasingMax3()
            }
        }
    }

    override fun part2(input: InputRepresentation): Int {
        val lines = input.map { it.split(" ").map(String::toLong) }
        return lines.count { nums ->
            nums.allIncreasingMax3() ||
                nums.allDecreasingMax3() ||
                nums.indices.any { i ->
                    val omittedOne = nums.omit(i)
                    omittedOne.allIncreasingMax3() ||
                        omittedOne.allDecreasingMax3()
                }
        }
    }
}

fun main() = Day02.run()