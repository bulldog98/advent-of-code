package year2023.day02

private fun String.toReveal(): Map<String, Int> = split(", ").associate {
    val (num, color) = it.split(" ")
    color to num.toInt()
}

private fun Map<String, Int>.isPossibleWith(allowed: Map<String, Int>) =
    !keys.any { it !in allowed } && all { (color, count) ->
        count <= (allowed[color] ?: 0)
    }

data class Game(val id: Int, val balls: List<Map<String, Int>>) {
    private constructor(id: String, ballStrings: String) :
            this(
                id.toInt(),
                ballStrings
                    .split("; ")
                    .map { it.toReveal() }
            )
    constructor(input: String) : this(input.split(": ")[0].drop(5), input.split(": ")[1])

    fun fewestPossible(): Map<String, Int> {
        val currentMinimum = mutableMapOf<String, Int>()
        balls.forEach { reveal ->
            reveal.forEach { (color, count) ->
                if ((currentMinimum[color] ?: 0) < count) {
                    currentMinimum[color] = count
                }
            }
        }
        return currentMinimum
    }

    fun isPossibleWith(allowedBalls: Map<String, Int>) =
        balls.all { reveal ->
            reveal.isPossibleWith(allowedBalls)
        }
}