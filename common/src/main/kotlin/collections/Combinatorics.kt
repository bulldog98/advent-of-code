package collections

fun <E> Collection<E>.pairings(): Sequence<Pair<E, E>> = when {
    isEmpty() || size == 1 -> emptySequence()
    else -> sequence {
        forEachIndexed { index, a ->
            forEachIndexed { index2, b ->
                if (index2 > index)
                    yield(a to b)
            }
        }
    }
}

fun <T : Comparable<T>> Collection<T>.allOrderings(): Sequence<List<T>> = when {
    isEmpty() -> emptySequence()
    size == 1 -> sequenceOf(this.toList())
    else -> asSequence().flatMap {
        (this - it).allOrderings().map { ordering -> listOf(it) + ordering }
    }
}