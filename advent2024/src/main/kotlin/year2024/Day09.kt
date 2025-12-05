package year2024

import adventday.AdventDay
import adventday.InputRepresentation

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

fun Array<Block?>.checkSum() = foldIndexed(0L) { index, acc, block ->
    if (block is OccupiedBlock)
        acc + index * block.id
    else
        acc
}

fun List<Block>.checkSum() = initialMemory().checkSum()

fun String.toMemory() = chunked(2)
    .map { it.toCharArray() }
    .flatMapIndexed { id, oneOrTwoDigits ->
        listOf(
            OccupiedBlock(id, oneOrTwoDigits[0].digitToInt()),
            FreeBlock(oneOrTwoDigits.getOrElse(1) { '0' }.digitToInt())
        ) }

object Day09 : AdventDay(2024, 9){
    override fun part1(input: InputRepresentation): Long {
        val blocks = input.lines[0].toMemory()
        val memory = blocks.initialMemory()

        while (true) {
            val firstFreeBlock = memory.indexOfFirst { it is FreeBlock }
            val blockToMove = memory.indexOfLast { it is OccupiedBlock }
            if (firstFreeBlock > blockToMove) break
            val freeBlock = memory[firstFreeBlock]
            memory[firstFreeBlock] = memory[blockToMove]
            memory[blockToMove] = freeBlock
        }
        return memory.checkSum()
    }

    override fun part2(input: InputRepresentation): Long {
        val blocks = input.lines[0].toMemory()
        val maxId = blocks.filterIsInstance<OccupiedBlock>().maxOf { it.id }
        return (maxId downTo 0).fold(blocks) { currentBlocks, id ->
            val blockToMove = currentBlocks.filterIsInstance<OccupiedBlock>().first { it.id == id }
            val blockToMovePosition = currentBlocks.indexOf(blockToMove)
            val firstFreeStorage = currentBlocks.filterIsInstance<FreeBlock>().firstOrNull {
                it.size >= blockToMove.size
            } ?: return@fold currentBlocks

            val firstFreeStoragePosition = currentBlocks.indexOf(firstFreeStorage)
            if (firstFreeStoragePosition > blockToMovePosition)
                currentBlocks
            else
                currentBlocks.subList(0, firstFreeStoragePosition) +
                    blockToMove + FreeBlock(firstFreeStorage.size - blockToMove.size) +
                    currentBlocks.subList(firstFreeStoragePosition + 1, blockToMovePosition) +
                    FreeBlock(blockToMove.size) +
                    currentBlocks.subList(blockToMovePosition + 1, currentBlocks.size)
        }.checkSum()
    }
}

fun main() = Day09.run()