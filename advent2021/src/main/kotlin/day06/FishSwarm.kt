package day06

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
