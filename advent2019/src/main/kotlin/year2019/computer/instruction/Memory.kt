package year2019.computer.instruction

class Memory(initialMemory: List<Long>): MutableList<Long> {
    private val backingMemory = initialMemory.toMutableList()
    override val size: Int
        get() = backingMemory.size

    override fun clear() = backingMemory.clear()

    override fun get(index: Int): Long {
        if (index in backingMemory.indices) {
            return backingMemory[index]
        }
        backingMemory += (backingMemory.size..index).map { 0 }
        return backingMemory[index]
    }

    override fun isEmpty(): Boolean = backingMemory.isEmpty()

    override fun iterator(): MutableIterator<Long> = error("not supported")

    override fun listIterator(): MutableListIterator<Long> = error("not supported")

    override fun listIterator(index: Int): MutableListIterator<Long> = error("not supported")

    override fun removeAt(index: Int): Long = backingMemory.removeAt(index)

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<Long> = backingMemory.subList(fromIndex, toIndex)

    override fun set(index: Int, element: Long): Long {
        if (index in backingMemory.indices) {
            return backingMemory.set(index, element)
        }
        backingMemory += (backingMemory.size..index).map { 0 }
        return backingMemory.set(index, element)
    }

    override fun retainAll(elements: Collection<Long>): Boolean = backingMemory.retainAll(elements)

    override fun removeAll(elements: Collection<Long>): Boolean = backingMemory.removeAll(elements)

    override fun remove(element: Long): Boolean = backingMemory.remove(element)

    override fun lastIndexOf(element: Long): Int = backingMemory.lastIndexOf(element)

    override fun indexOf(element: Long): Int = backingMemory.indexOf(element)

    override fun containsAll(elements: Collection<Long>): Boolean = backingMemory.containsAll(elements)

    override fun contains(element: Long): Boolean = backingMemory.contains(element)

    override fun addAll(index: Int, elements: Collection<Long>): Boolean = backingMemory.addAll(index, elements)

    override fun addAll(elements: Collection<Long>): Boolean = backingMemory.addAll(elements)

    override fun add(index: Int, element: Long) = backingMemory.add(index, element)

    override fun add(element: Long): Boolean = backingMemory.add(element)
}