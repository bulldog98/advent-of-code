package year2024

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import findAllPositionsOf
import helper.pair.mapSecond
import kotlin.math.absoluteValue

object Day15 : AdventDay(2024, 15) {
    private fun Char.toDirectionVector() = when (this) {
        '^' -> Point2D.UP
        '>' -> Point2D.RIGHT
        '<' -> Point2D.LEFT
        'v' -> Point2D.DOWN
        else -> error("invalid instruction")
    }
    data class Instruction(val char: Char, val direction: Point2D)

    data class Field(val boxes: Set<Point2D>, val walls: Set<Point2D>, val robotPosition: Point2D) {
        fun attemptMove(direction: Point2D): Field {
            assert(direction.x.absoluteValue <= 1 && direction.y.absoluteValue <= 1) { "can only move 1 space" }
            val nextRobotPosition = robotPosition + direction
            if (nextRobotPosition in walls) return this
            if (nextRobotPosition !in walls && nextRobotPosition !in boxes) return copy(robotPosition = nextRobotPosition)
            val afterMovingBox = attemptMoveBox(nextRobotPosition, direction) ?: return this
            return afterMovingBox.copy(robotPosition = nextRobotPosition)
        }

        private fun attemptMoveBox(boxPosition: Point2D, direction: Point2D): Field? {
            val nextPos = direction + boxPosition
            if (nextPos in walls) return null
            val movedTheBox = copy(boxes = boxes - boxPosition + nextPos)
            if (nextPos in boxes) return movedTheBox.attemptMoveBox(nextPos, direction)?.let { it.copy(boxes = it.boxes + nextPos) }
            return movedTheBox
        }


        // debug helper
        fun prettyPrint() = (0..walls.maxOf { it.y }).joinToString("\n") { y ->
            (0..walls.maxOf { it.x }).joinToString("") { x ->
                val point = Point2D(x, y)
                when (point) {
                    in walls -> "#"
                    robotPosition -> "@"
                    in boxes -> "O"
                    else -> "."
                }
            }
        }
    }

    override fun part1(input: InputRepresentation): Long {
        val (map, instructions) = input.asTwoBlocks().mapSecond {
            it.flatMap { line -> line.map { char -> Instruction(char, char.toDirectionVector()) } }
        }
        val boxes = map.findAllPositionsOf('O')
        val walls = map.findAllPositionsOf('#')
        val robotStartPosition = map.findAllPositionsOf('@').first()
        val initialField = Field(boxes, walls, robotStartPosition).also { println(it.prettyPrint()) }
        val finalConfig = instructions.fold(initialField) { acc, instruction ->
            acc.attemptMove(instruction.direction).also {
                // println()
                // println("Move ${instruction.char}")
                // println(it.prettyPrint())
            }
        }
        return finalConfig.boxes.sumOf { it.y * 100 + it.x }
    }

    override fun part2(input: InputRepresentation): Long {
        TODO("Not yet implemented")
    }
}

fun main() = Day15.run()