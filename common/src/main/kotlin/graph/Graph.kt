package graph

interface Graph<T> {
    val nodes: Collection<T>
    val edges: Collection<Pair<T, T>>
    fun neighborsOf(node: T): Collection<T> = edges.filter { (u, _) ->
        u == node
    }.map { (_, v) -> v }.toSet()
}
