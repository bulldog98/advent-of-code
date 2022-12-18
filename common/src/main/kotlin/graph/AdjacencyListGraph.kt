package graph

class AdjacencyListGraph<T>(
    override val nodes: Collection<T>,
    private val connections: (T) -> List<T>
) : Graph<T> {
    override val edges: Collection<Pair<T, T>>
        get() = nodes.flatMap { u ->
            connections(u).map { v -> u to v }
        }.toSet()

    override fun neighborsOf(node: T) =
        connections(node)
}