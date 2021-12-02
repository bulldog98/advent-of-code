typealias Direction = Pair<String, Int>

fun List<String>.toDirections() = map {
    val (a, b) = it.split(' ')
    a to b.toInt()
}

fun Direction.toMovement() = when (first) {
    "forward" -> 0 to second
    "up" -> -second to 0
    "down" -> second to 0
    else -> throw IllegalArgumentException("did not expect $first")
}

fun main() {
    fun part1(input: List<Direction>): Int {
        val (depth, hPos) = input.map { it.toMovement() }.fold(0 to 0) { (oldDepth, oldHPos), (depthGain, hPosGain) ->
            (oldDepth + depthGain) to (oldHPos + hPosGain)
        }
        return hPos * depth
    }

    fun part2(input: List<Direction>): Int {
        val (depth, hPos) = input.map { it.toMovement() }.fold(Triple(0, 0, 0)) { (oldDepth, oldHPos, oldAim), (depthGain, hPosGain) ->
            Triple(
            oldDepth + hPosGain * oldAim,
            oldHPos + hPosGain,
            oldAim + depthGain
            )
        }
        return hPos * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test").toDirections()
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02").toDirections()
    println(part1(input))
    println(part2(input))
}