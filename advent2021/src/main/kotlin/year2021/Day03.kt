package year2021

import adventday.AdventDay
import adventday.InputRepresentation
import kotlin.math.pow

private operator fun UInt.get(index: Int) = (this shr (UInt.SIZE_BITS - 1 - index)) % 2u

private fun UInt.invert(bits: Int) = inv() % 2.0.pow(bits).toUInt()

object Day03 : AdventDay(2021, 3) {
    override fun part1(input: InputRepresentation): Int {
        val parsed = input.lines.map { it: String -> it.toUInt(2) }
        val gamma = (0 until UInt.SIZE_BITS).toList().map { pos ->
            if (parsed.count { it[pos] == 1u } > (input.lines.size / 2)) {
                '1'
            } else
                '0'
        }.joinToString("").toUInt(2)
        val bitSize = UInt.SIZE_BITS - parsed.minOf { it.countLeadingZeroBits() }
        val epsilon = gamma.invert(bitSize)
        return gamma.toInt() * epsilon.toInt()
    }

    override fun part2(input: InputRepresentation): Int {
        val parsed = input.lines.map { it: String -> it.toUInt(2) }
        val bitSize = parsed.minOf { it.countLeadingZeroBits() }
        fun List<UInt>.mostCommonBitAtPos(pos: Int) = if (count { it[pos] == 0u } > count { it[pos] == 1u }) 0u else 1u
        fun List<UInt>.leastCommonBitAtPos(pos: Int) = if (mostCommonBitAtPos(pos) == 0u) 1u else 0u
        val oxygenRating = generateSequence(parsed to bitSize) { (lastList, pos) ->
            val mCB = lastList.mostCommonBitAtPos(pos)
            lastList.filter { it[pos] == mCB } to pos + 1
        }.first { it.first.size == 1 }.first[0].toInt()
        val scrubberRating = generateSequence(parsed to bitSize) { (lastList, pos) ->
            val mCB = lastList.leastCommonBitAtPos(pos)
            lastList.filter { it[pos] == mCB } to pos + 1
        }.first { it.first.size == 1 }.first[0].toInt()
        return oxygenRating * scrubberRating
    }
}

fun main() = Day03.run()
