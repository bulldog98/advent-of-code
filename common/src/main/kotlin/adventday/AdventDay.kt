package adventday

abstract class AdventDay(year: Int, day: Int) {
    private val inputFiles: InputFiles = InputFiles(year, day)

    abstract fun part1(input: InputRepresentation) : Any
    abstract fun part2(input: InputRepresentation) : Any
    fun run() {
        println(part1(inputFiles.inputFile))
        println(part2(inputFiles.inputFile))
    }

    fun testPart1(exampleSuffix: String = "test") = part1(inputFiles.getFileWithSuffix(exampleSuffix))
    fun testPart2(exampleSuffix: String = "test") = part2(inputFiles.getFileWithSuffix(exampleSuffix))
}