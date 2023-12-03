package year2023.day03

import Point2D

data class Board(
    private val content: Map<Point2D, FieldContent>
) : Map<Point2D, FieldContent> by content {
    companion object {
        private val digit = "(\\d)+".toRegex()
        private fun String.extractNumbersInRow(y: Int) =
            digit.findAll(this).flatMap {
                val positions = it.range.map { x -> Point2D(x = x, y = y) }
                val number = FieldContentNumber(it.value.toInt(), positions)
                positions.map { pos -> pos to number }
            }

        private fun String.extractSymbolsInRow(y: Int) =
            withIndex()
                .filter { !it.value.isDigit() && it.value != '.' }
                .map { (x, c) -> Point2D(x = x, y = y) to FieldContentSymbol(c) }

        operator fun invoke(input: List<String>): Board =
            input.asSequence()
                .withIndex()
                .flatMap { (y, line) ->
                    line.extractNumbersInRow(y) + line.extractSymbolsInRow(y)
                }.toMap()
                .let(::Board)
    }
}

val Board.allSymbols: List<Pair<Point2D, FieldContentSymbol>>
    get() = filterValues { it is FieldContentSymbol }
        .map { it.key to it.value as FieldContentSymbol }

fun Board.numbersSurrounding(position: Point2D) =
    position.neighborHood
        .mapNotNull { pos -> this[pos] }
        .filterIsInstance<FieldContentNumber>()
        .toSet()
