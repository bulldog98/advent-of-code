package year2015

import adventday.AdventDay
import adventday.InputRepresentation
import kotlinx.serialization.json.*

object Day12 : AdventDay(2015, 12, "JSAbacusFramework.io") {
    fun String.parse() = Json.decodeFromString<JsonElement>(this)

    fun JsonElement.sumUp(isValid: (JsonElement) -> Boolean = { true }): Long = when {
        !isValid(this) -> 0L
        this is JsonArray -> sumOf { it.sumUp(isValid) }
        this is JsonObject -> values.sumOf { it.sumUp(isValid) }
        this is JsonPrimitive && !this.isString -> this.content.toLong()
        else -> 0L
    }

    override fun part1(input: InputRepresentation): Long = input
        .lines[0]
        .parse()
        .sumUp()

    override fun part2(input: InputRepresentation): Long = input
        .lines[0]
        .parse()
        .sumUp {
            it !is JsonObject || !it.values.any { value -> value is JsonPrimitive && value.content == "red" }
        }
}

fun main() = Day12.run()
