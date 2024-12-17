package adventday

import download.ensureInputExists
import download.inputFile
import kotlinx.coroutines.runBlocking
import java.io.File

private val data = File("data")

class InputFiles(private val year: Int, private val day: Int) {
    val inputFile by lazy {
        InputRepresentation(runBlocking {
            ensureInputExists(year, day, data)
        })
    }

    fun getFileWithSuffix(suffix: String) =
        InputRepresentation(inputFile(year, day, data, "_$suffix"))
}