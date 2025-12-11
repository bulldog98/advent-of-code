package year2015

import adventday.AdventDay
import adventday.InputRepresentation

object Day16 : AdventDay(2015, 16, "Aunt Sue") {
    val foundData = mapOf(
        "children" to 3,
        "cats" to 7,
        "samoyeds" to 2,
        "pomeranian" to 3,
        "akitas" to 0,
        "vizslas" to 0,
        "goldfish" to 5,
        "trees" to 3,
        "cars" to 2,
        "perfumes" to 1
    )

    data class RememberedAboutAuntSue(
        val number: Int,
        val rememberedAbout: Map<String, Int>
    ) {
        operator fun get(index: String) = rememberedAbout[index]

        companion object {
            // Sue 1: goldfish: 4, cars: 1, samoyeds: 29
            fun parse(line: String): RememberedAboutAuntSue {
                val list = line.split(": ", ", ")
                val lookup = list.drop(1).chunked(2) { (a, b) ->
                    a to b.toInt()
                }.toMap()
                return RememberedAboutAuntSue(
                    number = list[0].drop("Sue ".length).toInt(),
                    rememberedAbout = lookup
                )
            }
        }
    }

    override fun part1(input: InputRepresentation): Int = input
        .lines
        .map(RememberedAboutAuntSue::parse)
        .single {
            it.rememberedAbout.all { (aspect, count) ->
                foundData[aspect] == count
            }
        }.number

    override fun part2(input: InputRepresentation): Int = input
        .lines
        .map(RememberedAboutAuntSue::parse)
        .first {
            it.rememberedAbout.all { (aspect, count) ->
                when (aspect) {
                    "cats", "trees" -> foundData[aspect]?.let { indicatedData ->
                        count > indicatedData
                    } ?: true
                    "pomeranians", "goldfish" -> foundData[aspect]?.let { indicatedData ->
                        count < indicatedData
                    } ?: true
                    else -> foundData[aspect] == count
                }
            }
        }
        .number
}

fun main() = Day16.run()
