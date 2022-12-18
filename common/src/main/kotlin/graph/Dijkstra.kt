package graph

import priorityqueue.PriorityQueue

fun <T> Graph<T>.dijkstra(
    start: T,
    cost: (T, T) -> Long
): Pair<(T) -> Long?, (T) -> T?> {
    val distance = mutableMapOf(start to 0L)
    val predecessor = mutableMapOf<T, T>()

    val queue = PriorityQueue(nodes) { distance[it] ?: Long.MAX_VALUE }
    while (queue.isNotEmpty()) {
        val u = queue.removeFirst()
        neighborsOf(u).forEach { v ->
            if (v in queue) {
                distance[u]?.let { it + cost(u, v) }?.also {  alternativeWay ->
                    if (distance[v] == null || alternativeWay < distance[v]!!) {
                        distance[v] = alternativeWay
                        predecessor[v] = u
                    }
                }
            }
        }
    }
    distance.remove(start)
    return distance::get to predecessor::get
}