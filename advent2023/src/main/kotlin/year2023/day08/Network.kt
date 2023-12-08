package year2023.day08

data class Network(val leftRightOrder: String, val instructions: Map<String, Pair<String, String>>) {
    constructor(input: List<String>): this(
        input[0],
        input.drop(2).associate { line ->
            val (node, left, right) = line.split("=", "(", ")", ",").filter { it.isNotBlank() }.map { it.trim() }
            node to (left to right)
        }
    )

    fun numberOfSteps(node: String, endCondition: (String) -> Boolean): Long {
        var currentNode = node
        var i = 0L
        while (!endCondition(currentNode)) {
            val (l, r) = instructions[currentNode]!!
            val inst = leftRightOrder[(i % leftRightOrder.length).toInt()]
            i++
            currentNode = when (inst) {
                'R' -> r
                'L' -> l
                else -> error("should not happen")
            }
        }
        return i
    }
}