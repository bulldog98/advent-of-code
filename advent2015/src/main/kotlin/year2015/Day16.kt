package year2015

import NotYetImplemented
import adventday.AdventDay
import adventday.InputRepresentation

object Day16 : AdventDay(2015, 16, "Aunt Sue") {
    /*
    children: 3
    cats: 7
    samoyeds: 2
    pomeranians: 3
    akitas: 0
    vizslas: 0
    goldfish: 5
    trees: 3
    cars: 2
    perfumes: 1
     */
    data class RememberedAboutAuntSue(
        val children: Int? = null,
        val cats: Int? = null,
        val samoyeds: Int? = null,
        val pomeranian: Int? = null,
        val akitas: Int? = null,
        val vizsalas: Int? = null,
        val goldfish: Int? = null,
        val trees: Int? = null,
        val cars: Int? = null,
        val perfumes: Int? = null,
        val number: Int
    ) {
        companion object {
            // Sue 1: goldfish: 4, cars: 1, samoyeds: 29
            fun parse(line: String): RememberedAboutAuntSue {
                val list = line.split(": ", ", ")
                val lookup = list.drop(1).chunked(2) { (a, b) ->
                    a to b.toInt()
                }.toMap()
                return RememberedAboutAuntSue(
                    children = lookup["children"],
                    cats = lookup["cats"],
                    samoyeds = lookup["samoyeds"],
                    pomeranian = lookup["pomeranian"],
                    akitas = lookup["akitas"],
                    vizsalas = lookup["vizsalas"],
                    goldfish = lookup["goldfish"],
                    trees = lookup["trees"],
                    cars = lookup["cars"],
                    perfumes = lookup["perfumes"],
                    number = list[0].drop("Sue ".length).toInt()
                )
            }
        }
    }

    override fun part1(input: InputRepresentation): Int = input
        .lines
        .map(RememberedAboutAuntSue::parse)
        .first {
            (it.children ?: 3) == 3 &&
                (it.cats ?: 7) == 7 &&
                (it.samoyeds ?: 2) == 2 &&
                (it.pomeranian ?: 3) == 3 &&
                (it.akitas ?: 0) == 0 &&
                (it.vizsalas ?: 0) == 0 &&
                (it.goldfish ?: 5) == 5 &&
                (it.trees ?: 3) == 3 &&
                (it.cars ?: 2) == 2 &&
                (it.perfumes ?: 1) == 1
        }.number

    override fun part2(input: InputRepresentation): Any =
        NotYetImplemented
}

fun main() = Day16.run()
