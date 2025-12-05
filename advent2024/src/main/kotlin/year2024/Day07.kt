package year2024

import adventday.AdventDay
import adventday.InputRepresentation
import helper.numbers.toAllLongs

data class Equation(val result: Long, val numbers: List<Long>) {
    companion object {
        fun parse(input: String): Equation = input.toAllLongs().toList().run {
            Equation(this[0], this.drop(1))
        }
    }
}

fun Equation.isFeasible(allOperatorsApplied: (Long, Long) -> List<Long>): Boolean {
    if (numbers.size == 2) return allOperatorsApplied(numbers[0], numbers[1]).any { it == result }
    // there are no negative numbers
    if (numbers[0] > result) return false
    return allOperatorsApplied(numbers[0], numbers[1]).map { Equation(result, listOf(it) + numbers.drop(2)) }
        .any { it.isFeasible(allOperatorsApplied) }
}

object Day07 : AdventDay(2024, 7) {
    override fun part1(input: InputRepresentation): Long {
        val equations = input.lines.map { it: String -> Equation.parse(it) }
        return equations.filter { it.isFeasible { a, b ->
            listOf(
                a + b,
                a * b,
            )
        } }.sumOf {
            it.result
        }
    }

    override fun part2(input: InputRepresentation): Long {
        val equations = input.lines.map { it: String -> Equation.parse(it) }
        return equations.filter { it.isFeasible { a, b ->
            listOf(
                a + b,
                a * b,
                "$a$b".toLong()
            )
        } }.sumOf {
            it.result
        }
    }
}

fun main() = Day07.run()