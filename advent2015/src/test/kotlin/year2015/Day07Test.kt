package year2015

import adventday.InputFiles
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import year2015.Day07.evaluateWire
import year2015.day07.asInstructions

class Day07Test {
    val inputFile = InputFiles(2015, 7)

    @Test
    fun `complete example evaluation`() {
        val instructions = inputFile
            .getFileWithSuffix("test")
            .asInstructions
        assertEquals(
            mapOf<String, UShort>(
                "d" to  72u,
                "e" to  507u,
                "f" to  492u,
                "g" to  114u,
                "h" to  65412u,
                "i" to  65079u,
                "x" to  123u,
                "y" to  456u,
            ),
            instructions.mapValues { (wire) ->
                instructions.evaluateWire(wire)
            }
        )
    }
}
