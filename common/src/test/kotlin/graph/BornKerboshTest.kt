package graph

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BornKerboshTest {
    private val exampleGraph: Graph<Int> = AdjacencyListGraph((1..6).toList()) {
        when (it) {
            1 -> listOf(2, 5)
            2 -> listOf(1, 3, 5)
            3 -> listOf(2, 4)
            4 -> listOf(3, 5, 6)
            5 -> listOf(1, 2, 4)
            6 -> listOf(4)
            else -> error("invalid vertex")
        }
    }


    @Test
    fun test() = assertEquals(
        listOf(
            setOf(1, 2, 5),
            setOf(2, 3),
            setOf(3, 4),
            setOf(4, 5),
            setOf(4, 6)
        ),
        exampleGraph.bornKerbosh().toList()
    )
}