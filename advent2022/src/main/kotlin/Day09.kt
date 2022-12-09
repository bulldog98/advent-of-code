import kotlin.math.sign

class Day09 : AdventDay<Int>(2022, 9) {
    sealed class RopeMove(val amount: Int) {
        val move: (Pair<Int, Int>) -> Pair<Int, Int> = when (this) {
            is Up -> { (a, b) -> a + 1 to b }
            is Down -> { (a, b) -> a - 1 to b }
            is Left -> { (a, b) -> a to b - 1 }
            is Right -> { (a, b) -> a to b + 1 }
        }
        companion object {
            fun from(input: String) =
                input.split(" ").let { (a, b) ->
                    when (a) {
                        "L" -> Left(b.toInt())
                        "R" -> Right(b.toInt())
                        "U" -> Up(b.toInt())
                        "D" -> Down(b.toInt())
                        else -> error("could not parse")
                    }
                }
        }
    }

    class Up(amount: Int) : RopeMove(amount)
    class Down(amount: Int) : RopeMove(amount)
    class Left(amount: Int) : RopeMove(amount)
    class Right(amount: Int) : RopeMove(amount)

    infix fun Pair<Int, Int>.adjecent(other: Pair<Int, Int>): Boolean =
        (first - other.first) in (-1 .. 1) &&
        (second - other.second) in (-1 .. 1)

    infix fun Pair<Int, Int>.follow(head: Pair<Int, Int>): Pair<Int, Int> =
        this.first + (head.first - this.first).sign to this.second + (head.second - this.second).sign

    private fun simulate(inst: List<RopeMove>, knots: MutableList<Pair<Int, Int>>): Int {
        val visited = mutableSetOf(0 to 0)
        inst.forEach { m ->
            repeat(m.amount) {
                knots[0] = m.move(knots[0])
                knots.drop(1).indices.forEach { index ->
                    val head = knots[index]
                    val tail = knots[index + 1]
                    if (!(head adjecent tail)) {
                        knots[index + 1] = tail follow head
                        visited += knots.last()
                    }
                }
            }
        }
        return visited.size
    }

    override fun part1(input: List<String>): Int = simulate(
        input.map { RopeMove.from(it) },
        MutableList(2) { 0 to 0}
    )

    override fun part2(input: List<String>): Int = simulate(
        input.map { RopeMove.from(it) },
        MutableList(10) { 0 to 0}
    )
}

fun main() = Day09().run()