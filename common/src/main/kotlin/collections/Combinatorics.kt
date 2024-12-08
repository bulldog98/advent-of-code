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