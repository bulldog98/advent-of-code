package year2023

import AdventDay
import helper.numbers.NUMBERS_REGEX

data class Mapping(
    val from: String,
    val to: String,
    val mapping: (Long) -> Long?
): (Long) -> Long? by mapping {
    companion object {
        private fun helper(line: String): (Long) -> Long? {
            val (to, start, size) = NUMBERS_REGEX.findAll(line).map { it.value.toLong() }.toList()
            val range = (start until start + size)
            return {
                if (it in range) {
                    it - start + to
                } else {
                    null
                }
            }
        }

        fun of(from: String, to: String, line: String) = Mapping(
            from,
            to,
            helper(line)
        )
    }
}

object Day05 : AdventDay(2023, 5) {
    override fun part1(input: List<String>): Any {
        val seeds = NUMBERS_REGEX.findAll(input[0]).map { it.value.toLong() }.toList()

        val inputs = input.joinToString("\n")
            .split("\n\n")
            .drop(1)
            .map(String::lines)
            .map { lines ->
                val (from, to) = lines.first().split("-to-", " map:")
                lines.drop(1).map {
                    Mapping.of(from, to, it)
                }
            }
        val res = inputs.fold(seeds) { last, mappings ->
            last.map { lastTypeNumber ->
                mappings.firstNotNullOfOrNull { mapping ->
                    mapping(lastTypeNumber)
                } ?: lastTypeNumber
            }
        }
        return res.min()
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day05.run()
