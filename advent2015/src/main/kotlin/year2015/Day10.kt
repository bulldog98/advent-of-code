package year2015

import NotYetImplemented
import adventday.AdventDay
import adventday.InputRepresentation

object Day10 : AdventDay(2015, 10, "Elves Look, Elves Say") {
    tailrec fun String.readSequence(prefix: String = ""): String = when {
        isEmpty() -> prefix
        else -> {
            val start = first()
            val count = takeWhile { it == start }.count()
             dropWhile { it == start }.readSequence(prefix + "$count$start")
        }
    }

    override fun part1(input: InputRepresentation): Int =
        generateSequence(input.lines[0]) {
            it.readSequence()
        }.drop(1)
            .take(40)
            .last()
            .length

    override fun part2(input: InputRepresentation): Any =
        NotYetImplemented
}

fun main() = Day10.run()
