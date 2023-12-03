package year2023

import AdventDay
import year2023.day03.Board
import year2023.day03.FieldContentNumber
import year2023.day03.allSymbols
import year2023.day03.numbersSurrounding

object Day03 : AdventDay(2023, 3) {
    override fun part1(input: List<String>) = with(Board(input)) {
        allSymbols.flatMap { (symbolPosition, _) ->
            numbersSurrounding(symbolPosition)
        }.sumOf { it.num }
    }

    override fun part2(input: List<String>): Int = with(Board(input)) {
        allSymbols
            .filter { (_, symbol) -> symbol.symbol == '*' }
            .map { (symbolPosition, _) ->
                numbersSurrounding(symbolPosition)
            }.filter { it.size == 2 }
            .sumOf {
                // somehow compile needs it, but idea thinks it's not needed
                it.fold<FieldContentNumber, Int>(1) { a, b ->
                    a * b.num
                }
            }
    }
}

fun main() = Day03.run()
