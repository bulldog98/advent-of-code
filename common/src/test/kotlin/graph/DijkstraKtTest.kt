package graph

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class DijkstraKtTest {
    // this example is from the german wikipedia https://de.wikipedia.org/wiki/Dijkstra-Algorithmus
    private val testGraph = AdjacencyListGraph(
        listOf(
            "Frankfurt", "Mannheim", "Würzburg", "Stuttgart",
            "Kassel", "Karlsruhe", "Erfurt", "Nürnberg", "Augsburg", "München"
        )
    ) {
        when (it) {
            "Frankfurt" -> listOf("Mannheim", "Würzburg", "Kassel")
            "Mannheim" -> listOf("Frankfurt", "Karlsruhe")
            "Würzburg" -> listOf("Frankfurt", "Erfurt", "Nürnberg")
            "Stuttgart" -> listOf("Nürnberg")
            "Kassel" -> listOf("Frankfurt", "München")
            "Karlsruhe" -> listOf("Mannheim", "Augsburg")
            "Erfurt" -> listOf("Würzburg")
            "Nürnberg" -> listOf("Würzburg", "Stuttgart", "München")
            "Augsburg" -> listOf("Karlsruhe", "München")
            "München" -> listOf("Augsburg", "Nürnberg", "Kassel")
            else -> error("node is not in list")
        }
    }
    private fun cost(u: String, v: String): Long = when (u to v) {
        "Frankfurt" to "Mannheim" -> 85
        "Mannheim" to "Karlsruhe" -> 80
        "Frankfurt" to "Kassel" -> 173
        "Frankfurt" to "Würzburg" -> 217
        "Würzburg" to "Nürnberg" -> 103
        "Würzburg" to "Erfurt" -> 186
        "Karlsruhe" to "Augsburg" -> 250
        "Augsburg" to "München" -> 84
        "Nürnberg" to "Stuttgart" -> 183
        "Nürnberg" to "München" -> 167
        "Kassel" to "München" -> 502
        else -> error("should not need this cost $u to $v")
    }

    @Test
    fun dijkstra() {
        val (dist, _) = testGraph.dijkstra("Frankfurt", ::cost)
        assertEquals(487, dist("München"))
    }
}