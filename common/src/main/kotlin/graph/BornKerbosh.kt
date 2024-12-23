package graph

fun <E> Graph<E>.bornKerbosh(
    r: Set<E> = emptySet(),
    initialP: Set<E> = nodes.toSet(),
    initialX: Set<E> = emptySet()
): Sequence<Set<E>> = sequence {
    if (initialP.isEmpty() && initialX.isEmpty()) yield(r)
    var p = initialP
    var x = initialX
    p.forEach { v ->
        yieldAll(bornKerbosh(r + v, p intersect neighborsOf(v).toSet(), x intersect neighborsOf(v).toSet()))
        p = p - v
        x = x + v
    }
}

fun <E> Graph<E>.enumerateAllMaxCliques() = bornKerbosh()