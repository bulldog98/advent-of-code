package adventday

import kotlin.time.measureTimedValue

abstract class AdventDay(val year: Int, val day: Int, val title: String = "unknown") {
    private val inputFiles: InputFiles = InputFiles(year, day)

    abstract fun part1(input: InputRepresentation) : Any
    abstract fun part2(input: InputRepresentation) : Any
    fun run() {
        println("--- AoC $year, Day $day: $title ---\n")
        measureTimedValue { part1(inputFiles.inputFile) }.let { (value, time) ->
            println("Part 1 solution: $value took $time")
        }
        measureTimedValue { part2(inputFiles.inputFile) }.let { (value, time) ->
            println("Part 2 solution: $value took $time")
        }
    }

    fun testPart1(exampleSuffix: String = "test") = part1(inputFiles.getFileWithSuffix(exampleSuffix))
    fun testPart2(exampleSuffix: String = "test") = part2(inputFiles.getFileWithSuffix(exampleSuffix))
}