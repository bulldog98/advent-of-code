package day03

import org.openjdk.jmh.annotations.*
import readInput
import java.util.concurrent.*

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 4)
@Measurement(iterations = 20, time = 1, timeUnit = TimeUnit.SECONDS)
class Day03Benchmark {
    private val inputFile = "day03/Day03"
    val input by lazy {
        // make file bigger (simple repeat is enough here)
        (1..3000).map { readInput(inputFile) }.reduce { a, b -> a + b }
    }

    @Setup
    fun setup() {
        input.size
    }

    @Benchmark
    fun part1Test(): Int = part1(input)

    @Benchmark
    fun part2Test(): Int = part2(input)
}