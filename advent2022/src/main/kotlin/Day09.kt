import kotlin.math.sign

class Day09 : AdventDay<Int>(2022, 9) {
    sealed class RopeMove(val amount: Int) {
        val move: (RopePair) -> RopePair  = when (this) {
            is Up -> { it -> it.copy(head = it.head.first + 1 to it.head.second) }
            is Down -> { it -> it.copy(head = it.head.first - 1 to it.head.second) }
            is Left -> { it -> it.copy(head = it.head.first to it.head.second - 1) }
            is Right -> { it -> it.copy(head = it.head.first to it.head.second + 1) }
        }

        val moveDir: (Pair<Int, Int>) -> Pair<Int, Int> = when (this) {
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
    data class RopePair(val head: Pair<Int, Int>, val tail: Pair<Int, Int>) {
        val stable: Boolean
            get() = (head.first - tail.first) in (-1 .. 1) &&
                    (head.second - tail.second) in (-1 .. 1)

        fun follow(): RopePair = copy(
            tail = tail.first + (head.first - tail.first).sign
                    to
                    tail.second + (head.second - tail.second).sign
        )

        fun simulate(inst: List<RopeMove>): Int {
            val tailVisited = mutableSetOf<Pair<Int, Int>>()
            var cur = this
            tailVisited.add(cur.tail)
            inst.forEach { m ->
                (0 until m.amount).forEach {
                    cur = m.move(cur)
                    if (!cur.stable) {
                        cur = cur.follow()
                    }
                    tailVisited.add(cur.tail)
                }
            }
            return tailVisited.size
        }
    }

    private fun simulate(inst: List<RopeMove>, knots: MutableList<Pair<Int, Int>>): Int {
        val visited = mutableSetOf(0 to 0)
        inst.forEach { m ->
            repeat(m.amount) {
                knots[0] = m.moveDir(knots[0])
                knots.drop(1).indices.forEach { index ->
                    val pair = RopePair(knots[index], knots[index + 1])
                    if (!pair.stable) {
                        knots[index + 1] = pair.follow().tail
                        visited += knots.last()
                    }
                }
            }
        }
        return visited.size
    }

    class Up(amount: Int) : RopeMove(amount)
    class Down(amount: Int) : RopeMove(amount)
    class Left(amount: Int) : RopeMove(amount)
    class Right(amount: Int) : RopeMove(amount)

    override fun part1(input: List<String>): Int =
        RopePair(0 to 0, 0 to 0)
            .simulate(input.map { RopeMove.from(it) })


    override fun part2(input: List<String>): Int = simulate(input.map { RopeMove.from(it) }, MutableList(10) { 0 to 0})
}

fun main() = Day09().run()