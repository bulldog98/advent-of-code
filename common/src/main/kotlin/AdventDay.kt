abstract class  AdventDay(year: Int, day: Int) {
    private val inputFiles: InputFiles = InputFiles(year, day)

    abstract fun part1(input: List<String>) : Any?
    abstract fun part2(input: List<String>) : Any?
    fun run() {
        println(part1(inputFiles.inputFile.readLines()))
        println(part2(inputFiles.inputFile.readLines()))
    }

    fun testPart1() = part1(inputFiles.testInput1.readLines())
    fun testPart2() = part2(inputFiles.testInput2.readLines())
}