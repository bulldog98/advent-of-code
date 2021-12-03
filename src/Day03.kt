import kotlin.math.pow

operator fun UInt.get(index: Int) = (this shr (UInt.SIZE_BITS - 1 - index)) % 2u

fun UInt.invert(bits: Int) = inv() % 2.0.pow(bits).toUInt()

fun main() {
    fun part1(input: List<UInt>): Int {
        val gamma = (0 until UInt.SIZE_BITS).toList().map { pos ->
            if (input.count { it[pos] == 1u } > (input.size / 2)) {
                '1'
            } else
                '0'
        }.joinToString("").toUInt(2)
        val bitSize = UInt.SIZE_BITS - input.minOf { it.countLeadingZeroBits() }
        val epsilon = gamma.invert(bitSize)
        return gamma.toInt() * epsilon.toInt()
    }

    fun part2(input: List<UInt>): Int {
        val bitSize = input.minOf { it.countLeadingZeroBits() }
        fun List<UInt>.mostCommonBitAtPos(pos: Int) = if (count { it[pos] == 0u } > count { it[pos] == 1u }) 0u else 1u
        fun List<UInt>.leastCommonBitAtPos(pos: Int) = if (mostCommonBitAtPos(pos) == 0u) 1u else 0u
        val oxygenRating = generateSequence(input to bitSize) { (lastList, pos) ->
            val mCB = lastList.mostCommonBitAtPos(pos)
            lastList.filter { it[pos] == mCB } to pos + 1
        }.first { it.first.size == 1 }.first[0].toInt()
        val scrubberRating = generateSequence(input to bitSize) { (lastList, pos) ->
            val mCB = lastList.leastCommonBitAtPos(pos)
            lastList.filter { it[pos] == mCB } to pos + 1
        }.first { it.first.size == 1 }.first[0].toInt()
        return oxygenRating * scrubberRating
    }

    val testInput = readInput("Day03_test").map { it.toUInt(2) }
    val input = readInput("Day03").map { it.toUInt(2) }
    // test if implementation meets criteria from the description:
    check(part1(testInput) == 198)
    println(part1(input))

    // test if implementation meets criteria from the description:
    check(part2(testInput) == 230)
    println(part2(input))
}