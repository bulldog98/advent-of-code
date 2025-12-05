package year2021

import adventday.AdventDay
import adventday.InputRepresentation
import year2021.day16.Packet

object Day16 : AdventDay(2021, 16) {

    override fun part1(input: InputRepresentation) = Packet.sumOf(Packet.parseHexString(input.text)) {
        it.version
    }

    override fun part2(input: InputRepresentation): Long =
        Packet.parseHexString(input.text).value
}

fun main() = Day16.run()
