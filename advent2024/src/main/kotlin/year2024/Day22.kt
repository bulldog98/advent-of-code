package year2024

import adventday.AdventDay
import adventday.InputRepresentation
import helper.numbers.toAllLongs

object Day22 : AdventDay(2024, 22) {
    private fun Long.generateSecrets() = generateSequence(this) { oldSecret ->
        val firstStep = ((oldSecret * 64) xor oldSecret) % 16777216
        val secondStep = ((firstStep / 32) xor firstStep) % 16777216
        ((secondStep * 2048) xor secondStep) % 16777216
    }

    override fun part1(input: InputRepresentation): Long = input.sumOf {
        val initialSecret = it.toAllLongs().first()
        initialSecret.generateSecrets().take(2001).last()
    }

    override fun part2(input: InputRepresentation): Long {
        TODO("Not yet implemented")
    }
}

fun main() = Day22.run()