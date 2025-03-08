package year2021

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import graph.AdjacencyListGraph
import graph.dijkstra
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import year2021.day15.*

object Day15 : AdventDay(2021, 15) {
    override fun part1(input: InputRepresentation): Long {
        val field = Field.of(input)
        val (distance) = field.asGraph()
            .dijkstra(Point2D(0, 0), field.costInSection(Point2D.ORIGIN))
        return (distance(field.bottomRight) ?: 0L)
    }

    // TODO: handles example, but not real input
    override fun part2(input: InputRepresentation): Long {
        val field = Field.of(input)
        val graph = field.asGraph()
        val sections = (0..<5L).flatMap { x -> (0..<5L).map { y -> Point2D(x, y) } }.toSet()
        val cache = buildMap<Long, (Point2D, Point2D) -> Long?> {
            val adjustedGraphs = sections.groupBy { (it.x + it.y) % 9L }
            adjustedGraphs.values.forEach { sections ->
                val startPoints = sections.flatMap { section -> graph.validStartPoints(section) }.distinct()
                val distanceFunctions = runBlocking {
                    startPoints.map { point ->
                        async(Dispatchers.Default) {
                            point to graph.dijkstra(point, field.costInSection(sections.first())).first
                        }
                    }.awaitAll().toMap()
                }
                this[sections.first().x + sections.first().y] = { from, to ->
                    distanceFunctions[from]?.let { it(to) }
                }
            }
        }.let {
            { point: Point2D ->
                it[(point.x + point.y) % 9L]!!
            }
        }
        val maxX = graph.nodes.maxOf { it.x }
        val maxY = graph.nodes.maxOf { it.y }
        val handleEndPoint: Point2D.(Point2D) -> List<Pair<Point2D, Point2D>> =  { section ->
            when {
                this.x == maxX && this.y == maxY -> listOf(
                    Point2D(0, this.y) to section + Point2D.RIGHT,
                    Point2D(this.x, 0) to section + Point2D.DOWN,
                )
                this.x == maxX -> listOf(Point2D(0, this.y) to section + Point2D.RIGHT)
                this.y == maxY -> listOf(Point2D(this.x, 0) to section + Point2D.DOWN)
                else -> error("no edge")
            }
        }
        val surroundingGraph = AdjacencyListGraph(
            sections.flatMap { (graph.validStartPoints(it) + graph.validEndPoints(it)).map { p -> p to it } }.toSet()
        ) { (point, section) ->
            when {
                point in graph.validStartPoints(section) && point in graph.validEndPoints(section) ->
                    (point.handleEndPoint(section) + graph.validEndPoints(section).map { it to section }).distinct() - (point to section)
                point in graph.validEndPoints(section) ->
                    point.handleEndPoint(section)
                point in graph.validStartPoints(section) ->
                    graph.validEndPoints(section).map { it to section }
                else -> error("every point has to be start or end point point: $point, section: $section")
            }
        }
        val (distance) = surroundingGraph.dijkstra(Point2D.ORIGIN to Point2D.ORIGIN) { (oldPoint, oldSection), (newPoint, newSection) ->
            when {
                oldSection == newSection -> cache(oldSection)(oldPoint, newPoint) ?: Long.MAX_VALUE
                else -> field.costInSection(newSection)(oldPoint, newPoint)
            }
        }
        return distance(field.bottomRight to Point2D(4, 4)) ?: 0L
    }
}

fun main() = Day15.run()
