package year2024

import adventday.AdventDay
import adventday.InputRepresentation
import helper.numbers.toAllLongs

object Day21: AdventDay(2024, 21) {
    // region numeric keypad
    private data class NumberKeyPad(val position: Char = 'A') {
        fun moveTo(char: Char): Pair<NumberKeyPad, List<String>> = copy(position = char) to position.getShortestWaysTo(char)

        fun waysToInput(input: String) = input.fold(this to listOf("")) { (numPad, ways), nextChar ->
            val (nextNumPad, furtherSteps) = numPad.moveTo(nextChar)
            nextNumPad to furtherSteps.flatMap { ways.map { w -> w + it + "A" } }
        }.second

        companion object {
            // +---+---+---+
            // | 7 | 8 | 9 |
            // +---+---+---+
            // | 4 | 5 | 6 |
            // +---+---+---+
            // | 1 | 2 | 3 |
            // +---+---+---+
            //     | 0 | A |
            //     +---+---+
            private val digitKeyNeighborLookup = mapOf(
                'A' to listOf('0' to '<', '3' to '^'),
                '0' to listOf('2' to '^', 'A' to '>'),
                '1' to listOf('4' to '^', '2' to '>'),
                '2' to listOf('1' to '<', '5' to '^', '3' to '>', '0' to 'v'),
                '3' to listOf('A' to 'v', '2' to '<', '6' to '^'),
                '4' to listOf('1' to 'v', '5' to '>', '7' to '^'),
                '5' to listOf('2' to 'v', '4' to '<', '6' to '>', '8' to '^'),
                '6' to listOf('3' to 'v', '5' to '<', '9' to '^'),
                '7' to listOf('4' to 'v', '8' to '>'),
                '8' to listOf('5' to 'v', '7' to '<', '9' to '>'),
                '9' to listOf('6' to 'v', '8' to '<')
            )

            private val memoizeShortestWays = mutableMapOf<Pair<Char, Pair<Char, String>>, List<String>>()
            private fun Char.getShortestWaysTo(other: Char, seen: String = ""): List<String> = memoizeShortestWays.getOrPut(this to (other to seen)) {
                val neighbors = digitKeyNeighborLookup[this]!!
                if (neighbors.any { it.first == other }) return@getOrPut listOf(neighbors.first { it.first == other }.second + "")
                val ways = when (other) {
                    this -> emptyList()
                    else -> digitKeyNeighborLookup[this]!!.filter { it.first !in seen }.flatMap { (nextChar, goDir) ->
                        nextChar.getShortestWaysTo(other, seen = seen + this).map {
                            goDir + it
                        }
                    }
                }
                if (ways.isNotEmpty()) {
                    val shortest = ways.filter { it.isNotEmpty() }.minOf { it.length }
                    ways.filter { it.length == shortest }
                } else
                    ways
            }
        }
    }
    // endregion

    // region direction keypad
    private data class DirectionKeyPad(val position: Char = 'A') {
        fun moveTo(char: Char): Pair<DirectionKeyPad, List<String>> = copy(position = char) to position.getShortestWaysTo(char)

        fun waysToInput(input: String) = input.fold(this to listOf("")) { (dirPad, ways), nextChar ->
            val (nextDirPad, furtherSteps) = dirPad.moveTo(nextChar)
            nextDirPad to furtherSteps.flatMap { ways.map { w -> w + it + "A" } }
        }.second

        companion object {
            //    +---+---+
            //    | ^ | A |
            //+---+---+---+
            //| < | v | > |
            //+---+---+---+
            private val digitKeyNeighborLookup = mapOf(
                'A' to listOf('^' to '<', '>' to 'v'),
                '^' to listOf('A' to '>', 'v' to 'v'),
                'v' to listOf('<' to '<', '>' to '>', '^' to '^'),
                '<' to listOf('v' to '>'),
                '>' to listOf('v' to '<', 'A' to '^')
            )

            private val memoizeShortestWays = mutableMapOf<Pair<Char, Pair<Char, String>>, List<String>>()
            private fun Char.getShortestWaysTo(other: Char, seen: String = ""): List<String> = memoizeShortestWays.getOrPut(this to (other to seen)) {
                val neighbors = digitKeyNeighborLookup[this]!!
                if (neighbors.any { it.first == other }) return@getOrPut listOf(neighbors.first { it.first == other }.second + "")
                val ways = when (other) {
                    this -> return@getOrPut listOf("")
                    else -> digitKeyNeighborLookup[this]!!.filter { it.first !in seen }.flatMap { (nextChar, goDir) ->
                        nextChar.getShortestWaysTo(other, seen = seen + this).map {
                            goDir + it
                        }
                    }
                }
                if (ways.isNotEmpty()) {
                    val shortest = ways.filter { it.isNotEmpty() }.minOf { it.length }
                    ways.filter { it.length == shortest }
                } else
                    ways
            }
        }
    }

    override fun part1(input: InputRepresentation): Long {
        val afterFirstRobot = input.map {
            it to NumberKeyPad('A').waysToInput(it)
        }
        val afterSecondRoboter = afterFirstRobot.map { (originalInput, ways) ->
            val ways2 = ways.flatMap { DirectionKeyPad().waysToInput(it) }
            val lengthOfShortestWay2 = ways2.minOf { it.length }
            val shortestWay2s = ways2.filter { it.length == lengthOfShortestWay2 }
            Triple(originalInput, ways, shortestWay2s)
        }

        val yourInputForSecondRobot = afterSecondRoboter.map { (originalInput, ways, secondWays) ->
            val ways3 = secondWays.flatMap { DirectionKeyPad().waysToInput(it) }
            val lengthOfShortestWay3 = ways3.minOf { it.length }
            val shortestWay3s = ways3.filter { it.length == lengthOfShortestWay3 }
            originalInput to shortestWay3s
        }

        return yourInputForSecondRobot.sumOf { (input, yourInput) ->
            input.toAllLongs().first() * yourInput[0].length
        }
    }

    override fun part2(input: InputRepresentation): Any {
        TODO("Not yet implemented")
    }
}

fun main() = Day21.run()