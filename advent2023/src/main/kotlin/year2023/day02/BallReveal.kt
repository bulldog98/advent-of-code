package year2023.day02

data class BallReveal(private val balls: Map<String, Int>) : Map<String, Int> by balls {
    private val colorsInterestedIn: List<String>
        get() = listOf("red", "green", "blue")

    val power: Int
        get() = colorsInterestedIn.fold(1) { cur, color ->
            cur * (this[color] ?: 0)
        }

    fun isPossibleWith(allowed: Map<String, Int>) =
        !keys.any { it !in allowed } && all { (color, count) ->
            count <= (allowed[color] ?: 0)
        }

    companion object {
        private fun parseBallRevealList(countPlusColorList: List<String>) = BallReveal(
            countPlusColorList.associate {
                it.split(" ").let { (num, color) ->
                    color to num.toInt()
                }
            }
        )

        fun parse(ballRevealString: String): BallReveal =
            parseBallRevealList(ballRevealString.split(", "))
    }
}
