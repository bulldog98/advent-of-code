package year2022

import AdventDay
import Point2D
import kotlin.math.sqrt

private fun List<String>.findAllWithChar(char: Char): Set<Point2D> = buildSet {
    this@findAllWithChar.forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            if (c == char) {
                add(Point2D(x.toLong(), y.toLong()))
            }
        }
    }
}

private fun moveInsideAs2d(
    into: Set<Point2D>
): Point2D.(Day22.Direction) -> Point2D = { direction: Day22.Direction ->
    when (direction) {
        year2022.Day22.Direction.UP, year2022.Day22.Direction.DOWN -> {
            val possibleNextStep = into.filter { it.x == x }
            val next = this + direction.asPointDiff
            when {
                next in possibleNextStep -> next
                direction == year2022.Day22.Direction.DOWN -> possibleNextStep.minBy { it.y }
                else -> possibleNextStep.maxBy { it.y }
            }
        }

        else -> {
            val possibleNextStep = into.filter { it.y == y }
            val next = this + direction.asPointDiff
            when {
                next in possibleNextStep -> next
                direction == year2022.Day22.Direction.RIGHT -> possibleNextStep.minBy { it.x }
                else -> possibleNextStep.maxBy { it.x }
            }
        }
    }
}

inline fun Point2D.move(
    length: Int,
    dir: Day22.Direction,
    field: Day22.FieldInformation,
    moveInDir: Point2D.(Day22.Direction) -> Point2D
): Point2D {
    var current = this
    repeat(length) {
        val next = current.moveInDir(dir)
        if (next in field.walls) {
            return current
        }
        current = next
    }
    return current
}

class Day22 : AdventDay(2022, 22) {
    enum class Direction(val asPointDiff: Point2D, val facing: Int) {
        RIGHT(Point2D.RIGHT, 0),
        DOWN(Point2D.DOWN, 1),
        LEFT(Point2D.LEFT, 2),
        UP(Point2D.UP, 3);

        fun turnRight() = when (this) {
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
            UP -> RIGHT
        }

        fun turnLeft() = when (this) {
            RIGHT -> UP
            DOWN -> RIGHT
            LEFT -> DOWN
            UP -> LEFT
        }
    }

    sealed interface Instruction {
        companion object {
            private tailrec fun parseAllHelper(
                restInput: String,
                currentList: List<Instruction>
            ): List<Instruction> = when {
                restInput.isEmpty() -> currentList
                restInput[0].isDigit() -> parseAllHelper(
                        restInput.dropWhile { it.isDigit() },
                        currentList + Move(restInput.takeWhile { it.isDigit() }.toInt())
                    )
                restInput[0] == 'L' ->
                    parseAllHelper(restInput.drop(1), currentList + LeftTurn)
                restInput[0] == 'R' ->
                    parseAllHelper(restInput.drop(1), currentList + RightTurn)
                else -> error("malformed input")
            }
            fun parseAll(input: String): List<Instruction> = parseAllHelper(input, emptyList())
        }
    }
    data class Move(val length: Int) : Instruction
    object LeftTurn: Instruction
    object RightTurn: Instruction
    data class FieldInformation(
        val field: Set<Point2D>,
        val walls: Set<Point2D>,
        val instructions: List<Instruction>
    ) {
        data class State(val position: Point2D, val direction: Direction)
        companion object {
            fun from(input: List<String>): FieldInformation {
                val fieldInput = input.dropLast(2)
                val freeTiles = fieldInput.findAllWithChar('.')
                val wallTiles = fieldInput.findAllWithChar('#')
                val regionSize = sqrt((freeTiles.size + wallTiles.size).toFloat() / 6).toInt()
                return FieldInformation(
                    input.dropLast(2).findAllWithChar('.'),
                    input.dropLast(2).findAllWithChar('#'),
                    Instruction.parseAll(input.last())
                )
            }
        }

        inline fun executeInstructions(
            wrap: Point2D.(Direction) -> Point2D
        ): Pair<Point2D, Direction> {
            val topRow = field.minOf { it.y }
            val topLeftOpen = field.filter { it.y == topRow }.minBy { it.x }
            var currentState = State(topLeftOpen, Direction.RIGHT)
            instructions.forEach {
                currentState = when (it) {
                    is LeftTurn -> currentState.copy(
                        direction = currentState.direction.turnLeft()
                    )
                    is RightTurn -> currentState.copy(
                        direction = currentState.direction.turnRight()
                    )
                    is Move -> currentState.copy(
                        position = currentState.position.move(
                            it.length,
                            currentState.direction,
                            this,
                            wrap
                        )
                    )
                }
            }
            return currentState.position to currentState.direction
        }
    }
    override fun part1(input: List<String>): Long {
        val field = FieldInformation.from(input)
        return field
            .executeInstructions(moveInsideAs2d(field.field + field.walls))
            .let { (pos, dir) ->
                (pos.y + 1) * 1000 + (pos.x + 1) * 4 + dir.facing
            }
    }

    override fun part2(input: List<String>): Long {
        val field = FieldInformation.from(input)
        return field // TODO: here has to be another function
            .executeInstructions(moveInsideAs2d(field.field + field.walls))
            .let { (pos, dir) ->
                (pos.y + 1) * 1000 + (pos.x + 1) * 4 + dir.facing
            }
    }
}

fun main() = Day22().run()