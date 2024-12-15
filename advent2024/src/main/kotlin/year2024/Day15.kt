package year2024

import Point2D
import adventday.AdventDay
import adventday.InputRepresentation
import findAllPositionsOf
import helper.pair.mapFirst
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
    sealed interface Box {
        operator fun contains(point: Point2D) = when (this) {
            is SimpleBox -> point == position
            is BigBox -> point == left || point == right
        }
        operator fun contains(box: Box) = when {
            this is SimpleBox && box is SimpleBox -> position == box.position
            this is BigBox && box is BigBox -> listOf(left, right).any { it in listOf(box.left, box.right) }
            else -> error("only one type of box is used together")
        }
        fun moveInDirection(direction: Point2D): Box
        val cost
            get() = when (this) {
                is SimpleBox -> position.y * 100 + position.x
                is BigBox -> 0L
            }
    }
    data class SimpleBox(val position: Point2D) : Box {
        override fun moveInDirection(direction: Point2D): SimpleBox = copy(position = position + direction)
    }

    data class BigBox(val left: Point2D, val right: Point2D) : Box {
        override fun moveInDirection(direction: Point2D): BigBox = copy(
            left = left + direction,
            right = right + direction
        )
    }
    operator fun Set<Point2D>.contains(box: Box) = when (box) {
        is SimpleBox -> box.position in this
        is BigBox -> box.left in this || box.right in this
    }

    data class Field(val boxes: Set<Box>, val walls: Set<Point2D>, val robotPosition: Point2D) {
        fun attemptMove(direction: Point2D): Field {
            assert(direction.x.absoluteValue <= 1 && direction.y.absoluteValue <= 1) { "can only move 1 space" }
            val nextRobotPosition = robotPosition + direction
            if (nextRobotPosition in walls) return this
            if (nextRobotPosition !in walls && boxes.all { nextRobotPosition !in it }) return copy(robotPosition = nextRobotPosition)
            val afterMovingBox = attemptMoveBox(boxes.first { nextRobotPosition in it }, direction) ?: return this
            return afterMovingBox.copy(robotPosition = nextRobotPosition)
        }

        private fun attemptMoveBox(box: Box, direction: Point2D): Field? {
            val movedBox = box.moveInDirection(direction)
            if (movedBox in walls) return null
            val movedTheBox = copy(boxes = boxes - box + movedBox)
            if (boxes.any { movedBox in it }) return movedTheBox.attemptMoveBox(movedBox, direction)?.let { it.copy(boxes = it.boxes + movedBox) }
            return movedTheBox
        }

        // debug helper
        fun prettyPrint() = (0..walls.maxOf { it.y }).joinToString("\n") { y ->
            (0..walls.maxOf { it.x }).joinToString("") { x ->
                val point = Point2D(x, y)
                when {
                    point in walls -> "#"
                    point == robotPosition -> "@"
                    boxes.filterIsInstance<SimpleBox>().any { point in it } -> "O"
                    boxes.filterIsInstance<BigBox>().any { it.left == point } -> "["
                    boxes.filterIsInstance<BigBox>().any { it.right == point } -> "]"
                    else -> "."
                }
            }
        }

        companion object {
            fun parse(mapLines: List<String>): Field {
                val boxes = mapLines.findAllPositionsOf('O').map { SimpleBox(it) }
                val walls = mapLines.findAllPositionsOf('#')
                val robotStartPosition = mapLines.findAllPositionsOf('@').first()
                return Field(boxes.toSet(), walls, robotStartPosition).also { println(it.prettyPrint()) }
            }
        }
    }

    override fun part1(input: InputRepresentation): Long {
        val (map, instructions) = input.asTwoBlocks().mapSecond {
            it.flatMap { line -> line.map { char -> Instruction(char, char.toDirectionVector()) } }
        }
        val initialField = Field.parse(map)
        val finalConfig = instructions.fold(initialField) { acc, instruction ->
            acc.attemptMove(instruction.direction).also {
                 println()
                 println("Move ${instruction.char}")
                 println(it.prettyPrint())
            }
        }
        return finalConfig.boxes.sumOf { it.cost }
    }

    override fun part2(input: InputRepresentation): Long {
        val (map, instructions) = input.asTwoBlocks().mapSecond {
            it.flatMap { line -> line.map { char -> Instruction(char, char.toDirectionVector()) } }
        }.mapFirst {
            it.map { line ->
                line.map { char ->
                    when (char) {
                        '#' -> "##"
                        'O' -> "[]"
                        '.' -> ".."
                        '@' -> "@."
                        else -> error("invalid char")
                    }
                }.joinToString("")
            }
        }
        val initialField = Field.parse(map)
        TODO("Not yet implemented")
    }
}

fun main() = Day15.run()