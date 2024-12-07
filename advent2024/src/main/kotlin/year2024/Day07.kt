package year2024

import AdventDay
import helper.numbers.toAllLongs

data class Equation(val result: Long, val numbers: List<Long>) {
    companion object {
        fun parse(input: String): Equation = input.toAllLongs().toList().run {
            Equation(this[0], this.drop(1))
        }
    }
}

fun Equation.isFeasible(): Boolean {
    if (numbers.size == 2) return result == numbers[0] + numbers[1] || result == numbers[0] * numbers[1]
    return listOf(
        Equation(result, listOf((numbers[0] + numbers[1])) + numbers.drop(2)),
        Equation(result, listOf((numbers[0] * numbers[1])) + numbers.drop(2)),
    ).any { it.isFeasible() }
}

object Day07 : AdventDay(2024, 7) {
    override fun part1(input: List<String>): Long {
        val equations = input.map(Equation::parse)
        return equations.filter { it.isFeasible() }.sumOf {
            it.result
        }
    }

    override fun part2(input: List<String>): Long {
        TODO("Not yet implemented")
    }
}

fun main() = Day07.run()