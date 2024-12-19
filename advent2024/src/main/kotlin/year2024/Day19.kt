package year2024

import adventday.AdventDay
import adventday.InputRepresentation
import helper.pair.mapFirst

object Day19 : AdventDay(2024, 19) {
    private data class CacheEntry(val existingTowels: List<String>, val wishedPattern: String)
    private val cache = mutableMapOf<CacheEntry, Boolean>()

    private fun List<String>.canArrange(wishedPattern: String): Boolean = cache.getOrPut(CacheEntry(this, wishedPattern)) {
        when {
            wishedPattern.isEmpty() -> true
            else -> any {
                wishedPattern.startsWith(it) && canArrange(wishedPattern.drop(it.length))
            }
        }
    }

    override fun part1(input: InputRepresentation): Int {
        val (patterns, towels) = input.asTwoBlocks().mapFirst { it.flatMap { it.split(", ") } }
        return towels.count { patterns.canArrange(it) }
    }

    override fun part2(input: InputRepresentation): Long {
        TODO("Not yet implemented")
    }
}

fun main() = Day19.run()