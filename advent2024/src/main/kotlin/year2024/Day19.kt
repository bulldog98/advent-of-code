package year2024

import adventday.AdventDay
import adventday.InputRepresentation
import helper.pair.mapFirst

object Day19 : AdventDay(2024, 19, "Linen Layout") {
    private data class CanArrangeCacheEntry(val existingTowels: Set<String>, val wishedPattern: String)

    private val canArrangeCache = mutableMapOf<CanArrangeCacheEntry, Boolean>()

    private fun Set<String>.canArrange(wishedPattern: String): Boolean =
        canArrangeCache.getOrPut(CanArrangeCacheEntry(this, wishedPattern)) {
            when {
                wishedPattern.isEmpty() -> true
                else -> any {
                    wishedPattern.startsWith(it) && canArrange(wishedPattern.drop(it.length))
                }
            }
        }

    private data class CountArrangementsCacheEntry(val existingTowels: Set<String>, val wishedPattern: String)

    private val countArrangementsCache = mutableMapOf<CountArrangementsCacheEntry, Long>()

    private fun Set<String>.countWaysToArrange(wishedPattern: String): Long =
        countArrangementsCache.getOrPut(CountArrangementsCacheEntry(this, wishedPattern)) {
            when {
                !canArrange(wishedPattern) -> 0
                wishedPattern.isEmpty() -> 1
                wishedPattern in this -> 1 + (this - wishedPattern).countWaysToArrange(wishedPattern)
                else -> sumOf {
                    if (wishedPattern.startsWith(it))
                        this.countWaysToArrange(wishedPattern.drop(it.length))
                    else
                        0
                }
            }
        }

    override fun part1(input: InputRepresentation): Int {
        val (existingTowels, wishedPatterns) = input.asTwoBlocks()
            .mapFirst { it.lines.flatMap { it.split(", ") } }
            .mapFirst { it.toSet() }
        return wishedPatterns.lines.count { existingTowels.canArrange(it) }
    }

    override fun part2(input: InputRepresentation): Long {
        val (existingTowels, wishedPatterns) = input.asTwoBlocks()
            .mapFirst { it.lines.flatMap { it.split(", ") } }
            .mapFirst { it.toSet() }
        return wishedPatterns.lines.sumOf { existingTowels.countWaysToArrange(it) }
    }
}

fun main() = Day19.run()