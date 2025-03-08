package year2021.day15

import Point2D
import graph.AdjacencyListGraph
import graph.Graph

fun Field.asGraph() = AdjacencyListGraph(
    keys,
    this::connectionFrom
)

fun Field.costInSection(section: Point2D): (Point2D, Point2D) -> Long = { _, to ->
    val originCost = this[to]!!.toLong()
    val adjustedCost = originCost + section.x + section.y
    if (adjustedCost > 9L)
        adjustedCost - 9L
    else
        adjustedCost
}

fun Graph<Point2D>.validStartPoints(section: Point2D) = when {
    section == Point2D.ORIGIN -> listOf(Point2D.ORIGIN)
    section.x == 0L -> nodes.filter { it.x == 0L }
    section.y == 0L -> nodes.filter { it.y == 0L }
    else -> nodes.filter { it.x == 0L || it.y == 0L }
}

fun Graph<Point2D>.validEndPoints(section: Point2D): List<Point2D> {
    val maxX by lazy { nodes.maxOf { it.x } }
    val maxY by lazy { nodes.maxOf { it.y } }
    return when {
        section.x == 5L && section.y == 5L -> listOf(Point2D(maxX, maxY))
        section.x == 5L -> nodes.filter { it.x == maxX }
        section.y == 5L -> nodes.filter { it.y == maxY }
        else -> nodes.filter { it.x == maxX || it.y == maxY }
    }
}