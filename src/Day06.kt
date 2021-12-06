typealias FishSwarm = Map<Int, Long>

fun String.toFishSwarm(): FishSwarm = splitToSequence(",")
    .groupBy { it }
    .mapValues { (_, v) -> v.size.toLong() }
    .mapKeys { (k, _) -> k.toInt() }

fun FishSwarm.nextDayOf(timeToReproduce: Int): Pair<Int, Long?> = when(timeToReproduce) {
    6 -> {
        val reproduced = this[0] ?: 0
        val aged = this[7] ?: 0
        6 to if ((reproduced + aged) == 0L) null else reproduced + aged
    }
    8 -> 8 to this[0]
    else -> timeToReproduce to this[timeToReproduce + 1]
}

@Suppress("UNCHECKED_CAST")
val FishSwarm.nextDay
    get(): FishSwarm = (0..8).associate { nextDayOf(it) }.filterValues { it != null } as FishSwarm

fun FishSwarm.stepXDays(x: Int) = generateSequence(this) { it.nextDay }.drop(x).first()

fun main() {
    fun part1(input: List<String>): Long {
        val fish = input[0].toFishSwarm()
        return fish.stepXDays(80).values.fold(0L, Long::plus)
    }

    fun part2(input: List<String>): Long {
        val fish = input[0].toFishSwarm()
        return fish.stepXDays(256).values.fold(0L, Long::plus)
    }

    val testInput = readInput("Day06_test")
    val input = readInput("Day06")
    // test if implementation meets criteria from the description:
    check(part1(testInput) == 5934L)
    println(part1(input))

    // test if implementation meets criteria from the description:
    check(part2(testInput) == 26984457539L)
    println(part2(input))
}