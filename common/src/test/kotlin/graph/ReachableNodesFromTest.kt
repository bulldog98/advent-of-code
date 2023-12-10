package graph

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ReachableNodesFromTest {
    @Test
    fun `should give all nodes reachable from start`() {
        val start = 1
        val graph = AdjacencyListGraph(
            nodes = (0..50).toSet()
        ) {
            listOf(it + 2).filter { i -> i in 0..50 }
        }

        val result = graph.reachableNodesFrom(start)

        Assertions.assertEquals(
            (1..50).toSet().filter { it % 2 == 1 }.toSet(),
            result
        )
    }
}