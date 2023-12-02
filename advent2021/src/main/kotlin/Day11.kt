import day11.computeStep
import day11.parseBoard

object Day11 : AdventDay(2021, 11) {
    override fun part1(input: List<String>): Int {
        val board = input.parseBoard()
        return (0..99).fold(board to 0) { old, _ ->
            val (nextBoard, flashed) = old.first.computeStep()
            nextBoard to flashed + old.second
        }.second
    }

    override fun part2(input: List<String>): Any {
        TODO("Not yet implemented")
    }
}


fun main() = Day11.run()
