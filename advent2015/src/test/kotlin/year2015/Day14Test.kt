package year2015

import adventday.InputFiles
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import year2015.Day14.Reindeer

class Day14Test {
    val day = Day14
    val inputFiles = InputFiles(2015, 14)

    @Test
    fun part1() = assertEquals(
        listOf(1120, 1056),
        inputFiles.getFileWithSuffix("test").lines.map(Reindeer::parse).map { it.distanceAfter(1000)}
    )
}
