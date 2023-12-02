package year2022

import AdventDay
import kotlin.math.absoluteValue

/**
 * this swaps beforeFirst -> first -> second -> afterSecond,
 * to beforeFirst -> second -> first -> afterSecond
 */
private fun swap(first: Day20.Node, second: Day20.Node) {
    val beforeFirst = first.previous
    val afterSecond = second.next

    // correct afterSecond to be after first
    afterSecond.previous = first
    first.next = afterSecond
    // correct second to be before first
    first.previous = second
    second.next = first
    // correct beforeFirst to be before second
    second.previous = beforeFirst
    beforeFirst.next = second
}

class Day20 : AdventDay(2022, 20) {

    class CyclicLinkedList {
        lateinit var start: Node
        lateinit var content: List<Node>

        companion object {
            fun from(input: List<Long>, key: Long = 1): CyclicLinkedList {
                val list = CyclicLinkedList()

                list.content = input.mapIndexed { index, data ->
                    Node(
                        index,
                        // adjust data to cycle length, so we do not need to move unnecessarily
                        data * key % (input.size - 1),
                        data
                        // start is for groveCoordinates, says it starts at value 0
                    ).also { if (data == 0L) list.start = it }
                }

                // setup prev and next for every element in the middle
                list.content.windowed(2).forEach { (a, b) ->
                    a.next = b
                    b.previous = a
                }
                val first = list.content.first()
                val last = list.content.last()
                first.previous = last
                last.next = first
                return list
            }
        }
        fun groveCoordinates(): List<Int> = buildList {
            var current = start
            repeat(3) {
                current = current[1000]
                add(current.id)
            }
        }
        fun mixing() {
            content.forEach { node ->
                repeat(node.data.absoluteValue.toInt()) {
                    if (node.data < 0) {
                        swap(node.previous, node)
                    } else {
                        swap(node, node.next)
                    }
                }
            }
        }

        operator fun get(index: Int): Node = content[index]
    }
    data class Node(val id: Int, val data: Long, val originalData: Long) {
        lateinit var previous: Node
        lateinit var next: Node
        operator fun get(index: Int): Node {
            var current = this
            repeat(index) {
                current = current.next
            }
            return current
        }
    }

    override fun part1(input: List<String>): Long {
        val list = CyclicLinkedList.from(input.map { it.toLong() })
        list.mixing()
        return list.groveCoordinates().sumOf { list[it].data }
    }

    override fun part2(input: List<String>): Long {
        val key = 811589153L
        val list = CyclicLinkedList.from(input.map { it.toLong() }, key)
        // mix 10 times
        repeat(10) { list.mixing() }
        // use originValue to compute sum, since data is % cycle length
        return list.groveCoordinates().sumOf { list[it].originalData * key }
    }
}

fun main() = Day20().run()