package adventday

import download.ensureInputExists
import download.inputFile
import kotlinx.coroutines.runBlocking
import java.io.File

private val data = File("data")

class InputFiles(year: Int, day: Int) {
    val inputFile by lazy {
        InputRepresentation(runBlocking {
            ensureInputExists(year, day, data)
        })
    }
    private val defaultTestInput by lazy {
        inputFile(year, day, data, "_test")
    }
    private val defaultTestInput1 by lazy {
        inputFile(year, day, data, "_test1")
    }
    private val defaultTestInput2 by lazy {
        inputFile(year, day, data, "_test2")
    }
    val testInput1 by lazy {
        InputRepresentation(if (defaultTestInput.exists()) {
            defaultTestInput
        } else {
            defaultTestInput1
        })
    }
    val testInput2 by lazy {
        InputRepresentation(if (defaultTestInput.exists()) {
            defaultTestInput
        } else {
            defaultTestInput2
        })
    }
}