package adventday

abstract class AdventDay(val year: Int, val day: Int, val title: String = "unknown") {
    private val inputFiles: InputFiles = InputFiles(year, day)

    abstract fun part1(input: InputRepresentation) : Any
    abstract fun part2(input: InputRepresentation) : Any
    fun run() {
        println("--- AoC $year, Day $day: $title ---\n")
        println(part1(inputFiles.inputFile))
        println(part2(inputFiles.inputFile))
    }

    fun testPart1(exampleSuffix: String = "test") = part1(inputFiles.getFileWithSuffix(exampleSuffix))
    fun testPart2(exampleSuffix: String = "test") = part2(inputFiles.getFileWithSuffix(exampleSuffix))
}