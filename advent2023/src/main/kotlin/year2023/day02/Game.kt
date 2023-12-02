package year2023.day02

data class Game(val id: Int, val balls: List<BallReveal>) {
    private constructor(id: String, ballStrings: String) :
            this(
                id
                    .toInt(),
                ballStrings
                    .split("; ")
                    .map(BallReveal::parse)
            )
    constructor(input: String) : this(input.split(": ")[0].drop(5), input.split(": ")[1])

    fun fewestPossible(): BallReveal {
        val currentMinimum = mutableMapOf<String, Int>()
        balls.forEach { reveal ->
            reveal.forEach { (color, count) ->
                if ((currentMinimum[color] ?: 0) < count) {
                    currentMinimum[color] = count
                }
            }
        }
        return BallReveal(currentMinimum)
    }

    fun isPossibleWith(allowedBalls: Map<String, Int>) =
        balls.all { reveal ->
            reveal.isPossibleWith(allowedBalls)
        }
}