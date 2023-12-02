package year2021.day04

data class GameInput(
    val drawnNumbers: Sequence<Int>,
    val boards: List<Board>
)

fun List<String>.toGameInput() = GameInput(
    drawnNumbers = this[0].splitToSequence(',').map { it.toInt() },
    boards = this.drop(1).filter { it.isNotBlank() }.windowed(5, 5).map { it.toBoard() }
)