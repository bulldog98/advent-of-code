package year2024

import AdventDay

private const val PARSE_MULTIPLICATIONS = """mul\((\d+),(\d+)\)"""
private const val DO_NOT_INSTRUCTION = """don't()"""
private const val DO_INSTRUCTION = """do()"""

object Day03 : AdventDay(2024, 3) {
    override fun part1(input: List<String>): Long =
        PARSE_MULTIPLICATIONS.toRegex()
            .findAll(input.joinToString(""))
            .sumOf {
                val (x, y) = it.destructured
                x.toLong() * y.toLong()
            }

    override fun part2(input: List<String>): Long {
        val allInstructions = input.joinToString("")
        val instructionsToComplete = buildList {
            var rest = allInstructions
            while (rest.isNotEmpty()) {
                val newInstructions = rest.split(DO_NOT_INSTRUCTION)[0]
                add(newInstructions)
                rest = rest.drop(newInstructions.length)
                val ignoredInstructions = rest.split(DO_INSTRUCTION)[0]
                rest = rest.drop(ignoredInstructions.length)
            }
        }
        return PARSE_MULTIPLICATIONS.toRegex()
            .findAll(instructionsToComplete.joinToString(""))
            .sumOf {
                val (x, y) = it.destructured
                x.toLong() * y.toLong()
            }
    }
}

fun main() = Day03.run()