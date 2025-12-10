package year2015

import adventday.InputFiles
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day10Test {
    val day = Day10
    val inputFiles = InputFiles(2015, 10)

    @Test
    fun correctReading() = with(day) {
        assertEquals(listOf(
            "11",
            "21",
            "1211",
            "111221",
            "312211"
        ), inputFiles.getFileWithSuffix("test").lines.map { it .readSequence()})
    }
}
