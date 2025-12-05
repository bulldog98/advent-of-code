package year2021.day04

import adventday.InputRepresentation

data class GameInput(
    val drawnNumbers: Sequence<Int>,
    val boards: List<Board>
)

fun InputRepresentation.toGameInput() = asSplitByEmptyLine().let { blocks ->
    GameInput(
        drawnNumbers = blocks[0].text.splitToSequence(',').map { it.toInt() },
        boards = blocks.drop(1).map(InputRepresentation::lines).map { it.toBoard() }
    )
}