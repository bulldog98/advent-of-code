import day06.stepXDays
import day06.toFishSwarm

object Day06 : AdventDay(2021, 6) {
    override fun part1(input: List<String>): Long {
        val fish = input[0].toFishSwarm()
        return fish.stepXDays(80).values.fold(0L, Long::plus)
    }

    override fun part2(input: List<String>): Long {
        val fish = input[0].toFishSwarm()
        return fish.stepXDays(256).values.fold(0L, Long::plus)
    }
}

fun main() = Day06.run()
