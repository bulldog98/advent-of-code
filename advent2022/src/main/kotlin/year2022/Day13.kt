package year2022

import adventday.AdventDay
import adventday.InputRepresentation
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

sealed class Elem : Comparable<Elem>
data class ListElem(val elem: List<Elem>) : Elem() {
    override fun compareTo(other: Elem): Int = when (other) {
        is ListElem -> when {
            elem.isEmpty() && other.elem.isNotEmpty() -> -1
            elem.isNotEmpty() && other.elem.isEmpty() -> 1
            elem.isEmpty() -> 0
            else -> {
                val a = this.elem[0]
                val b = other.elem[0]

                val result = a.compareTo(b)
                if (result == 0) {
                    ListElem(this.elem.drop(1)).compareTo(ListElem(other.elem.drop(1)))
                } else {
                    result
                }
            }
        }
        is IntElem -> this.compareTo(ListElem(listOf(other)))
    }
}
data class IntElem(val elem: Int): Elem() {
    override fun compareTo(other: Elem): Int = when (other) {
        is IntElem -> elem.compareTo(other.elem)
        is ListElem -> ListElem(listOf(this)).compareTo(other)
    }
}

fun JsonElement.convert(): Elem = when (this) {
    is JsonArray -> ListElem(map { it.convert() })
    is JsonPrimitive -> IntElem(content.toInt())
    else -> error("not parse")
}

fun String.parse(): Elem {
    val json = Json.decodeFromString<JsonArray>(this)
    return json.convert()
}

class Day13 : AdventDay(2022, 13) {
    override fun part1(input: InputRepresentation): Int {
        val pairWise = input.chunked(3)
        val stuff = pairWise.mapIndexed { i, (a, b) ->
            a.parse().compareTo(b.parse()) to i + 1
        }.filter { (c) -> c <= 0 }
        return stuff.sumOf { (_, i) -> i }
    }


    override fun part2(input: InputRepresentation): Int {
        val marker1 = ListElem(listOf(ListElem(listOf(IntElem(2)))))
        val marker2 = ListElem(listOf(ListElem(listOf(IntElem(6)))))
        val lines = input
            .asSequence()
            .filter { it.isNotEmpty() }
            .map{
                it.parse()
            }
            .plus(marker1)
            .plus(marker2)
            .sortedWith(Elem::compareTo)
            .toList()
        return (1 + lines.indexOfFirst { it.compareTo(marker1) == 0 }) *
                (1 + lines.indexOfFirst { it.compareTo(marker2) == 0 })
    }
}

fun main() = Day13().run()