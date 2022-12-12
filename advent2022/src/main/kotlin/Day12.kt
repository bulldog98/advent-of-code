import java.lang.Integer.min

class Day12 : AdventDay(2022, 12) {
    data class Graph(
        val nodes: List<Pair<Int, Int>>,
        val connections: Map<Pair<Int, Int>, List<Pair<Int, Int>>>,
        val height: Map<Pair<Int, Int>, Char>) {
        companion object {
            private fun List<String>.atPoint(point: Pair<Int, Int>) = when (this[point.second][point.first]) {
                'S' -> 'a'
                'E' -> 'z'
                else -> this[point.second][point.first]
            }

            fun of(input: List<String>): Graph {
                val nodes = input[0].indices.flatMap {  x ->
                    (input.indices).map { y -> x to y }
                }
                val height = buildMap {
                    input[0].indices.forEach { x ->
                        input.indices.forEach { y ->
                            this[x to y] = input.atPoint(x to y)
                        }
                    }
                }
                val connections = buildMap {
                    input[0].indices.forEach { x ->
                        input.indices.forEach { y ->
                            val ownHeight = input.atPoint(x to y)
                            val reachableNeighbors = listOf(x - 1 to y, x + 1 to y, x to y - 1, x to y + 1).filter { (a, b) ->
                                a in input[0].indices && b in input.indices &&
                                        ownHeight + 1 >= input.atPoint(a to b)
                            }
                            this[x to y] = reachableNeighbors
                        }
                    }
                }
                return Graph(nodes, connections, height)
            }
        }
    }

    private fun Graph.deikstra(from: Pair<Int, Int>): Map<Pair<Int, Int>, Int> {
        val (distance, predecessor) = init(from)
        val q = this.nodes.toMutableList()
        while (q.isNotEmpty()) {
            val u = q.minBy { distance[it] ?: Int.MAX_VALUE }
            q.remove(u)
            val neighbors = connections[u] ?: emptyList()
            neighbors.forEach { v ->
                if (v in q) {
                    updateDistance(u, v, distance, predecessor)
                }
            }
        }
        return distance
    }

    private fun updateDistance(
        u: Pair<Int, Int>,
        v: Pair<Int, Int>,
        distance: MutableMap<Pair<Int, Int>, Int>,
        predecessor: MutableMap<Pair<Int, Int>, Pair<Int, Int>>
    ) {
        val differentWay = if (distance[u] != Int.MAX_VALUE) {
            distance[u]!! + 1
        } else Int.MAX_VALUE
        if (differentWay < (distance[v] ?: Int.MAX_VALUE)) {
            distance[v] = differentWay
            predecessor[v] = u
        }
    }

    private fun Graph.init(from: Pair<Int, Int>): Pair<MutableMap<Pair<Int, Int>, Int>, MutableMap<Pair<Int, Int>, Pair<Int, Int>>> {
        val distance = buildMap {
            nodes.forEach { coord ->
                this[coord] = if (coord == from)
                    0
                else
                    Int.MAX_VALUE
            }
        }.toMutableMap()
        val predecessor = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()
        return distance to predecessor
    }

    override fun part1(input: List<String>): Int {
        val graph = Graph.of(input)
        var from = -1 to -1
        var to = -1 to -1
        input[0].indices.forEach { x ->
            input.indices.forEach { y ->
                val c = input[y][x]
                if (c == 'S') {
                    from = x to y
                }
                if (c == 'E') {
                    to = x to y
                }
            }
        }
        val dist = graph.deikstra(from)
        return dist[to] ?: Int.MAX_VALUE
    }

    override fun part2(input: List<String>): Int {
        val graph = Graph.of(input)
        var to = -1 to -1
        val startingPoints = mutableListOf<Pair<Int, Int>>()
        input[0].indices.forEach { x ->
            input.indices.forEach { y ->
                val c = input[y][x]
                if (c == 'a' || c == 'S') {
                    startingPoints += x to y
                }
                if (c == 'E') {
                    to = x to y
                }
            }
        }
        return startingPoints.fold(Int.MAX_VALUE) { curMin, from ->
            val dist = graph.deikstra(from)
            min(curMin, dist[to] ?: Int.MAX_VALUE)
        }
    }
}

fun main() = Day12().run()