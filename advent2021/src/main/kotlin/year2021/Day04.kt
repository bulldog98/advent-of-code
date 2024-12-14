package year2021

import adventday.AdventDay
import adventday.InputRepresentation
import year2021.day04.Board
import year2021.day04.toGameInput

private fun Board.isWinning(drawn: Collection<Int>): Boolean {
    val rowIsWinning = rows.any { row -> row.cells.count { drawn.contains(it) } == 5 }
    val columnIsWinning = columns.any { column -> column.count { drawn.contains(it) } == 5 }
    return rowIsWinning || columnIsWinning
}

private fun Board.score(lastNumber: Int, drawn: Collection<Int>) =
    lastNumber * cells.filter { !drawn.contains(it) }.sum()

object Day04 : AdventDay(2021, 4) {
    override fun part1(input: InputRepresentation): Int {
        val gameInput = input.toGameInput()
        val (drawn, last) = gameInput.drawnNumbers
            .scan(setOf<Int>() to -1) { (acc, _), curr ->
                acc + curr to curr
            }
            .first { (drawn) ->
                gameInput.boards.any { it.isWinning(drawn) }
            }
        val winningBoard = gameInput.boards.find { it.isWinning(drawn) } ?: throw Error("no winning board found")
        return winningBoard.score(last, drawn)
    }

    override fun part2(input: InputRepresentation): Int {
        val gameInput = input.toGameInput()
        val (drawn, _, last) = gameInput.drawnNumbers
            .scan(Triple(setOf<Int>(),-1, -1)) { (acc, _, last), curr ->
                Triple(acc + curr, last, curr)
            }
            .first { (drawn) ->
                gameInput.boards.none { !it.isWinning(drawn) }
            }
        val losingBoard = gameInput.boards.find { !it.isWinning(drawn - last) } ?: throw Error("no losing board found")
        return losingBoard.score(last, drawn)
    }
}

fun main() = Day04.run()
