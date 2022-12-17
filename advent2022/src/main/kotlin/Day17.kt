import day17.*

class Day17 : AdventDay(2022, 17) {
    private val lineHorizontalShape = setOf(0 xy 0, 1 xy 0, 2 xy 0, 3 xy 0)
    private val lineVerticalShape   = setOf(0 xy 0, 0 xy 1, 0 xy 2, 0 xy 3)
    private val plusShape           = setOf(1 xy 0, 0 xy 1, 1 xy 1, 2 xy 1, 1 xy 2)
    private val lShape              = setOf(0 xy 0, 1 xy 0, 2 xy 0, 2 xy 1, 2 xy 2)
    private val blockShape          = setOf(0 xy 0, 0 xy 1, 1 xy 0, 1 xy 1)
    private val shapes = listOf(lineHorizontalShape, plusShape, lShape, lineVerticalShape, blockShape)
    override fun part1(input: List<String>): Long {
        val dirs = input[0].map { dirFrom(it) }
        var chamber = Chamber(dirs = dirs, shapes = shapes)
        for (step in 0 until 2022) {
            chamber = chamber.simulateOneStep(step)
        }
        return chamber.rocks.maxOf { it.y } + 1
    }

    override fun part2(input: List<String>): Long {
        val dirs = input[0].map { dirFrom(it) }
        val cycleInfo = CycleInfo.findFor(Chamber(dirs = dirs, shapes = shapes))

        val rocksToJumpOver = 1_000_000_000_000 - cycleInfo.cycleStartHeight
        val cyclesToJump = rocksToJumpOver / cycleInfo.cycleLength
        val extraRocks = (rocksToJumpOver % cycleInfo.cycleLength).toInt()

        return (cyclesToJump * cycleInfo.cycleHeight) + cycleInfo.partialHeights[extraRocks]
    }
}

fun main() = Day17().run()