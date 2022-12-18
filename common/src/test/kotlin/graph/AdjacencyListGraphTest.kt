package graph

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AdjacencyListGraphTest {
    private val graph = AdjacencyListGraph(
        (0..4).toSet()
    ) { (0..it).toList() }
    @Test
    fun `neighbors is correct`() {
        assertEquals((0..4).toList(), graph.neighborsOf(4))
    }

    @Test
    fun `edges are computed correctly`() {
        val edges = graph.edges

        assertTrue(4 to 0 in edges)
    }
}