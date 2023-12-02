data class Game(val id: Int, val balls: List<Map<String, Int>>)

fun String.toReveal(): Map<String, Int> = split(", ").associate {
    val (num, color) = it.split(" ")
    color to num.toInt()
}

fun String.toGame() = split(": ").let { (g, ballsString) ->
    val id = g.drop(5).toInt()
    Game(id, ballsString.split("; ").map { it.toReveal() })
}

fun Map<String, Int>.isPossibleWith(allowed: Map<String, Int>) =
    !keys.any { it !in allowed } && all { (color, count) ->
        count <= (allowed[color] ?: 0)
    }

object Day02 : AdventDay(2023, 2) {
    private val allowedMax = mapOf(
        "red" to 12,
        "green" to 13,
        "blue" to 14
    )
    override fun part1(input: List<String>): Int =
        input.map {
            it.toGame()
        }.sumOf {
            val impossible = it.balls.any { reveal ->
                !reveal.isPossibleWith(allowedMax)
            }
            if (impossible) 0 else it.id
        }


    override fun part2(input: List<String>): Any =
        TODO("Not yet implemented")
}

fun main() = Day02.run()
