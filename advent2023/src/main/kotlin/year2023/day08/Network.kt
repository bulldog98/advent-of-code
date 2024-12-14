package year2023.day08

data class Network(val leftRightOrder: String, val instructions: Map<String, Pair<String, String>>) {
    constructor(leftRightOrder: String, instructionLines: List<String>): this(
        leftRightOrder,
        instructionLines.associate { line ->
            val (node, left, right) = line.split("=", "(", ")", ",").filter { it.isNotBlank() }.map { it.trim() }
            node to (left to right)
        }
    )

    fun numberOfSteps(node: String, endCondition: (String) -> Boolean): Long =
        followingInstructionsFrom(node).first { endCondition(it.first) }.second

    private fun followingInstructionsFrom(node: String) =
        generateSequence(node to 0L) { (node, i) ->
            val inst = leftRightOrder[(i % leftRightOrder.length).toInt()]
            val (l, r) = instructions[node]!!
            when (inst) {
                'R' -> r to i +1
                'L' -> l to i +1
                else -> error("should not happen")
            }
        }
}