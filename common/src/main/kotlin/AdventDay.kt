import download.ensureInputExists
import download.inputFile
import kotlinx.coroutines.runBlocking
import java.io.File

private val data = File("data")

abstract class  AdventDay<T>(year: Int, day: Int) {
    private val input by lazy {
        runBlocking {
            ensureInputExists(year, day, data)
        }
    }
    private val defaultTestInput by lazy {
        inputFile(year, day, data, "_test")
    }
    private val testInput by lazy {
        if (!defaultTestInput.exists()) {
            error("file does not exist give test input")
        }
        defaultTestInput.readLines()
    }
    abstract fun part1(input: List<String>) : T
    abstract fun part2(input: List<String>) : T
    fun run() {
        println(part1(input))
        println(part2(input))
    }

    fun testPart1() = part1(testInput)
    fun testPart2() = part2(testInput)
}