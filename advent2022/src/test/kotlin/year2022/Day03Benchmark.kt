package year2022

import adventday.InputRepresentation
import download.ensureInputExists
import kotlinx.coroutines.runBlocking
import org.openjdk.jmh.annotations.*
import java.io.File
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 4)
@Measurement(iterations = 20, time = 1, timeUnit = TimeUnit.SECONDS)
class Day03Benchmark {
    val day03 = Day03()
    val input by lazy {
        val origin = runBlocking {
            ensureInputExists(2022, 3, File("data")).readLines()
        }
        InputRepresentation((1..3000).map { origin }.reduce { a, b -> a + b })
    }

    @Setup
    fun setup() {
        input.size
    }

    @Benchmark
    fun part1Test(): Int = day03.part1(input)

    @Benchmark
    fun part2Test(): Int = day03.part1(input)
}