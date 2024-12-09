package year2024

import AdventDay

sealed interface Block {
    val size: Int
    fun initialRepresentation(): String
}

data class FreeBlock(override val size: Int) : Block {
    override fun initialRepresentation(): String = (0..<size).joinToString("") { "." }
}

data class OccupiedBlock(val id: Int, override val size: Int) : Block {
    override fun initialRepresentation(): String = "[" + (0..<size).joinToString("") { "$id" } + "]"
}

fun List<Block>.initialMemory(): Array<Block?> = arrayOfNulls<Block?>(sumOf { it.size }).also { blocks ->
    var index = 0
    forEach { block ->
        (index..< index + block.size).forEach { i ->
            blocks[i] = block
        }
        index += block.size
    }
}

object Day09 : AdventDay(2024, 9){
    override fun part1(input: List<String>): Long {
        val blocks = input[0].chunked(2)
            .map { it.toCharArray() }
            .flatMapIndexed { id, oneOrTwoDigits ->
                listOf(
                    OccupiedBlock(id, oneOrTwoDigits[0].digitToInt()),
                    FreeBlock(oneOrTwoDigits.getOrElse(1) { '0' }.digitToInt())
                ) }
        val memory = blocks.initialMemory()

        while (true) {
            val firstFreeBlock = memory.indexOfFirst { it is FreeBlock }
            val blockToMove = memory.indexOfLast { it is OccupiedBlock }
            if (firstFreeBlock > blockToMove) break
            val freeBlock = memory[firstFreeBlock]
            memory[firstFreeBlock] = memory[blockToMove]
            memory[blockToMove] = freeBlock
        }
        println("endloop")
        return memory.filterIsInstance<OccupiedBlock>().foldIndexed(0L) { index, acc, block ->
            acc + index * block.id
        }
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day09.run()