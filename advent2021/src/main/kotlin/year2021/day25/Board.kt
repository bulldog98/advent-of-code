package year2021.day25

import Point2D
import adventday.InputRepresentation
import helper.pair.mapFirst
import helper.pair.mapSecond

data class Board(
    private val data: Map<Point2D, FieldContent>,
    private val bottomRight: Point2D
) : Map<Point2D, FieldContent> by data {
    fun simulate(): Sequence<Board> = sequence {
        yield(this@Board)
        val next: Board = this@Board.simulateOneStep()
        yieldAll(next.simulate())
    }

    private fun Point2D.constrainToBoard(): Point2D =
        Point2D(
            x = x % bottomRight.x,
            y = y % bottomRight.y
        )

    private val Point2D.canBeMovedTo: Boolean
        get() = data[this] != null

    private fun FieldContent.moveIfPossibleFrom(position: Point2D) =
        move(position)
            .constrainToBoard()
            .let { nextPosition ->
                if (nextPosition.canBeMovedTo) {
                    position
                } else {
                    nextPosition
                }
            }

    private inline fun <reified T : FieldContent> simulateStepOf(): Board =
        data.entries
            .partition { it.value is T }
            .mapFirst { movingHerds ->
                movingHerds.associateBy({ it.key }) { it.value }
            }.mapFirst {
                it.mapKeys { (position, herd) ->
                    herd.moveIfPossibleFrom(position)
                }.toMap()
            }.mapSecond { nonMovingHerds ->
                nonMovingHerds.associateBy({ it.key }) { it.value }
            }.let { (movedHerds, nonMovingHerds) ->
                copy(data = movedHerds + nonMovingHerds)
            }

    private fun simulateOneStep(): Board = this
        .simulateStepOf<FieldContent.EastMovingSeaCucumberHerd>()
        .simulateStepOf<FieldContent.SouthMovingSeaCucumberHerd>()

    companion object {
        operator fun invoke(input: InputRepresentation): Board {
            val maxY = input.lines.size
            val maxX = input.lines.maxOf { it.length }
            val map = input
                .asCharMap { FieldContent(it) != null}
                .mapValues { FieldContent(it.value) ?: error("should not happen") }
            return Board(map, Point2D(x = maxX, y = maxY))
        }
    }
}