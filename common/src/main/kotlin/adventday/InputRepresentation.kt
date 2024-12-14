package adventday

import Point2D
import helper.numbers.toAllLongs
import helper.pair.mapFirst
import helper.pair.mapSecond
import java.io.File

/*
 * this class represents the data, it assumes that the backing file exists
 */
class InputRepresentation(private val backingFile: BackingOperations): List<String> by backingFile.readLines() {
    /**
     * this constructor is intended for testing purposes
     */
    constructor(fileContent: String) : this(BackingOperations.StringBacking(fileContent))
    /**
     * this constructor is intended for testing purposes
     */
    constructor(fileContent: List<String>) : this(BackingOperations.StringListBacking(fileContent))
    constructor(file: File) : this(BackingOperations.FileBacking(file))

    fun asText() = backingFile.readText()
    fun asSplitByEmptyLine() = asText().split("\n\n")
    fun asNumbers() = asText().toAllLongs()

    /**
     * split by empty line take exactly 2
     */
    fun asTwoBlocks(): Pair<List<String>, List<String>> = asSplitByEmptyLine()
        .zipWithNext()
        .first()
        .mapFirst { it.lines() }
        .mapSecond { it.lines() }

    fun asCharMap(validChar: (Char) -> Boolean = { true }) = buildMap {
        forEachIndexed { y, it ->
            it.forEachIndexed { x, c ->
                if (validChar(c)) {
                    put(Point2D(x, y), c)
                }
            }
        }
    }

    sealed interface BackingOperations {
        fun readText(): String
        fun readLines(): List<String>

        data class FileBacking(private val file: File) : BackingOperations {
            override fun readText(): String = file.readText()
            override fun readLines(): List<String> = file.readLines()
        }

        data class StringBacking(private val string: String) : BackingOperations {
            override fun readText(): String = string
            override fun readLines(): List<String> = string.lines()
        }

        data class StringListBacking(private val stringList: List<String>) : BackingOperations {
            override fun readText(): String = stringList.joinToString("\n")
            override fun readLines(): List<String> = stringList
        }
    }
}