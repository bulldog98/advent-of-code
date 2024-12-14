package adventday

import helper.numbers.toAllLongs
import java.io.File

/*
 * this class represents the data, it assumes that the backing file exists
 */
class InputRepresentation(private val backingFile: File): List<String> by backingFile.readLines() {
    fun asText() = backingFile.readText()
    fun asSplitByEmptyLine() = asText().split("\n\n")
    fun asNumbers() = asText().toAllLongs()
}