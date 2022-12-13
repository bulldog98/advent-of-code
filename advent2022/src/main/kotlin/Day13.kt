import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

sealed class Elem
data class ListElem(val elem: List<Elem>) : Elem()
data class IntElem(val elem: Int): Elem()

fun ListElem.compareRest(other: ListElem): Int {
    val a = this.elem[0]
    val b = other.elem[0]

    val result = a.compareTo(b)
    return if (result == 0) {
        ListElem(this.elem.drop(1)).compareTo(ListElem(other.elem.drop(1)))
    } else {
        result
    }
}

operator fun Elem.compareTo(other: Elem): Int = when {
    this is IntElem && other is IntElem -> this.elem.compareTo(other.elem)
    this is ListElem && other is ListElem -> {
        val foo = if (this.elem.isEmpty() && other.elem.isNotEmpty()) {
            -1
        } else if (this.elem.isNotEmpty() && other.elem.isEmpty()) {
            1
        } else if (this.elem.isEmpty()) {
            0
        } else null
        foo ?: this.compareRest(other)
    }
    this is IntElem && other is ListElem -> ListElem(listOf(this)).compareTo(other)
    this is ListElem && other is IntElem -> this.compareTo(ListElem(listOf(other)))
    else -> error("other")
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
    override fun part1(input: List<String>): Int {
        val pairWise = input.chunked(3)
        val stuff = pairWise.mapIndexed { i, (a, b) ->
            a.parse().compareTo(b.parse()) to i + 1
        }.filter { (c) -> c <= 0 }
        return stuff.sumOf { (_, i) -> i }
    }


    override fun part2(input: List<String>): Int {
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