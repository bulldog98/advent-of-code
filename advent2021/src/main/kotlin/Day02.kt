private typealias Direction = Pair<String, Int>

private fun List<String>.toDirections() = map {
    val (a, b) = it.split(' ')
    a to b.toInt()
}

private fun Direction.toMovement() = when (first) {
    "forward" -> 0 to second
    "up" -> -second to 0
    "down" -> second to 0
    else -> throw IllegalArgumentException("did not expect $first")
}

object Day02 : AdventDay(2021, 2) {
    override fun part1(input: List<String>): Int {
        val (depth, hPos) = input.toDirections()
            .map { it.toMovement() }
            .fold(0 to 0) { (oldDepth, oldHPos), (depthGain, hPosGain) ->
                (oldDepth + depthGain) to (oldHPos + hPosGain)
            }
        return hPos * depth
    }

    override fun part2(input: List<String>): Int {
        val (depth, hPos) = input.toDirections()
            .map { it.toMovement() }
            .fold(Triple(0, 0, 0)) { (oldDepth, oldHPos, oldAim), (depthGain, hPosGain) ->
                Triple(
                    oldDepth + hPosGain * oldAim,
                    oldHPos + hPosGain,
                    oldAim + depthGain
                )
            }
        return hPos * depth
    }
}

fun main() = Day02.run()
