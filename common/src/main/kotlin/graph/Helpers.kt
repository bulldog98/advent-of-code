package graph

fun <T> Graph<T>.reachableNodesFrom(node: T): Set<T> =
    buildSet {
        val workQueue = ArrayDeque<T>()
        workQueue.add(node)
        while (workQueue.isNotEmpty()) {
            val cur = workQueue.removeFirst()
            this += cur
            workQueue.addAll(neighborsOf(cur))
        }
    }