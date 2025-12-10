package year2021

import adventday.AdventDay
import adventday.InputRepresentation
import helper.pair.mapFirst
import helper.pair.mapSecond
import year2021.day13.Instructions

object Day13 : AdventDay(2021, 13, "Transparent Origami") {
    override fun part1(input: InputRepresentation) =
        Instructions(input.asTwoBlocks().mapFirst { it.lines }.mapSecond { it.lines })
            .folded()
            .drop(1)
            .first()
            .points.size

    // only prints out the board, you have to read the letters yourself
    override fun part2(input: InputRepresentation) =
        Instructions(input.asTwoBlocks().mapFirst { it.lines }.mapSecond { it.lines })
            .also { println(it) }
            .folded()
            .last()
            .fancyPrint()
}

fun main() = Day13.run()
