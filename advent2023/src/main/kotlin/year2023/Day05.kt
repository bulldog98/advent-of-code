package year2023

import adventday.AdventDay
import adventday.InputRepresentation
import helper.numbers.NUMBERS_REGEX

data class Mapping(
    val range: LongRange,
    val mapping: (Long) -> Long?
): (Long) -> Long? by mapping {
    companion object {
        private fun LongRange.toMapFunction(to: Long): (Long) -> Long? {
            return {
                if (it in this) {
                    it - this.first + to
                } else {
                    null
                }
            }
        }

        private fun helper(line: String): LongRange {
            val (_, start, size) = NUMBERS_REGEX.findAll(line).map { it.value.toLong() }.toList()
            return (start until start + size)
        }

        fun of(line: String) = Mapping(
            helper(line),
            helper(line).toMapFunction(NUMBERS_REGEX.find(line)?.value?.toLong()!!)
        )
    }
}

fun LongRange.splitBy(mappings: List<Mapping>): List<LongRange> = when {
    isEmpty() -> emptyList()
    mappings.none { first in it.range || last in it.range } -> listOf(this)
    else -> {
        val firstMapping = mappings.firstOrNull { first in it.range }
        if (firstMapping != null) {
            val newEnd = last { it in firstMapping.range }
            listOf(first..newEnd) + (newEnd + 1..last).splitBy(mappings)
        } else {
            val startOfRange = first { mappings.any { m -> it in m.range } }
            listOf((first until  startOfRange)) + (startOfRange..last).splitBy(mappings)
        }
    }
}

object Day05 : AdventDay(2023, 5) {
    private fun List<String>.computeMappings() =
        map(String::lines)
            .map { lines ->
                lines.drop(1).map {
                    Mapping.of(it)
                }
            }

    override fun part1(input: InputRepresentation): Any {
        val seeds = NUMBERS_REGEX.findAll(input[0]).map { it.value.toLong() }.toList()

        val mappings = input.asSplitByEmptyLine().drop(1).computeMappings()
        val res = mappings.fold(seeds) { last, mapping ->
            last.map { lastTypeNumber ->
                mapping.firstNotNullOfOrNull { mapping ->
                    mapping(lastTypeNumber)
                } ?: lastTypeNumber
            }
        }
        return res.min()
    }

    override fun part2(input: InputRepresentation): Any {
        val seedRanges = NUMBERS_REGEX.findAll(input[0])
            .map { it.value.toLong() }
            .chunked(2)
            .map { (start, size) -> (start until start + size) }
            .toList()

        val mappings = input.asSplitByEmptyLine().drop(1).computeMappings()

        val res = mappings.fold(seedRanges) { last, mapping ->
            last.flatMap { range ->
                    range.splitBy(mapping)
                }.map { range ->
                    mapping
                        .firstOrNull { range.first in it.range }
                        ?.let {
                            val firstMapped = it(range.first) ?: error("could not map ${range.first} with $it")
                            val lastMapped = it(range.last) ?: error("could not map ${range.last} with $it")
                            firstMapped..lastMapped
                        } ?: range
                }
        }
        return res.minOf { it.first }
    }
}

fun main() = Day05.run()
