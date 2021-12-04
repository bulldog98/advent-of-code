import day04.Board
import day04.GameInput
import day04.toGameInput

private fun Board.isWinning(drawn: Collection<Int>): Boolean {
    val rowIsWinning = rows.any { row -> row.cells.count { drawn.contains(it) } == 5 }
    val columnIsWinning = columns.any { column -> column.count { drawn.contains(it) } == 5 }
    return rowIsWinning || columnIsWinning
}

private fun Board.score(lastNumber: Int, drawn: Collection<Int>) =
    lastNumber * cells.filter { !drawn.contains(it) }.sum()

fun main() {
    fun part1(input: GameInput): Int {
        val (drawn, last) = input.drawnNumbers
            .scan(setOf<Int>() to -1) { (acc, _), curr ->
                acc + curr to curr
            }
            .first { (drawn) ->
                input.boards.any { it.isWinning(drawn) }
            }
        val winningBoard = input.boards.find { it.isWinning(drawn) } ?: throw Error("no winning board found")
        return winningBoard.score(last, drawn)
    }

    fun part2(input: GameInput): Int {
        val (drawn, _, last) = input.drawnNumbers
            .scan(Triple(setOf<Int>(),-1, -1)) { (acc, _, last), curr ->
                Triple(acc + curr, last, curr)
            }
            .first { (drawn) ->
                input.boards.none { !it.isWinning(drawn) }
            }
        val losingBoard = input.boards.find { !it.isWinning(drawn - last) } ?: throw Error("no losing board found")
        return losingBoard.score(last, drawn)
    }

    val testInput = readInput("Day04_test").toGameInput()
    val input = readInput("Day04").toGameInput()
    // test if implementation meets criteria from the description:
    check(part1(testInput) == 4512)
    println(part1(input))

    // test if implementation meets criteria from the description:
    check(part2(testInput) == 1924)
    println(part2(input))
}