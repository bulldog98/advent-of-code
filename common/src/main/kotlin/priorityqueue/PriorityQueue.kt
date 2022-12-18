package priorityqueue

private class SetAsSortedList<T, R : Comparable<R>>(val set: Set<T>, val sorting: (T) -> R): List<T> {
    override val size: Int
        get() = set.size
    override fun get(index: Int): T = set.sortedBy(sorting)[index]
    override fun isEmpty(): Boolean = set.isEmpty()
    override fun iterator(): Iterator<T> = set.sortedBy(sorting).iterator()
    override fun listIterator(): ListIterator<T> = set.sortedBy(sorting).listIterator()
    override fun listIterator(index: Int): ListIterator<T> = set.sortedBy(sorting).listIterator(index)
    override fun subList(fromIndex: Int, toIndex: Int): List<T> =
        set.sortedBy(sorting).subList(fromIndex, toIndex)
    override fun lastIndexOf(element: T): Int = set.sortedBy(sorting).lastIndexOf(element)
    override fun indexOf(element: T): Int = set.sortedBy(sorting).indexOf(element)
    override fun containsAll(elements: Collection<T>): Boolean = set.containsAll(elements)
    override fun contains(element: T): Boolean = element in set
}

class PriorityQueue<T, R : Comparable<R>> private constructor(
    private val content: MutableSet<T> = mutableSetOf(),
    private val cost: (T) -> R,
): List<T> by SetAsSortedList(content, cost) {
    override val size: Int
        get() = content.size
    constructor(elements: Collection<T>, cost: (T) -> R) : this(elements.toMutableSet(), cost)
    fun add(element: T) = content.add(element)
    fun addAll(elements: Collection<T>) = content.addAll(elements)
    fun removeFirst(): T {
        val elem = content.minBy(cost)
        content.remove(elem)
        return elem
    }
}