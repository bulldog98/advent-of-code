@file:Suppress("Unused")
package adventday

import Point2D
import helper.numbers.toAllLongs
import java.io.File

/*
 * this class represents the data, it assumes that the backing file exists
 */
class InputRepresentation(private val backingFile: BackingOperations) {
    /**
     * this constructor is intended for testing purposes
     */
    constructor(fileContent: String) : this(BackingOperations.StringBacking(fileContent))
    /**
     * this constructor is intended for testing purposes
     */
    constructor(fileContent: List<String>) : this(BackingOperations.StringListBacking(fileContent))
    constructor(file: File) : this(BackingOperations.FileBacking(file))

    val lines: List<String> by lazy { backingFile.readLines() }
    val lineSequence: Sequence<String> by lazy { lines.asSequence() }
    val text: String by lazy { backingFile.readText() }
    val sections: List<InputRepresentation> by lazy {
        text.split("\n\n")
            .map { InputRepresentation(it) }
    }
    fun asSplitByEmptyLine() = sections
    fun asNumbers() = text.toAllLongs()

    /**
     * split by empty line take exactly 2
     */
    fun asTwoBlocks(): Pair<InputRepresentation, InputRepresentation> = asSplitByEmptyLine()
        .zipWithNext()
        .first()

    fun asCharMap(validChar: (Char) -> Boolean = { true }) = buildMap {
        backingFile.readLines().forEachIndexed { y, it ->
            it.forEachIndexed { x, c ->
                if (validChar(c)) {
                    put(Point2D(x, y), c)
                }
            }
        }
    }

    // used for printing map with additional stuff painted in
    fun addIntoMap(replace: (Point2D) -> Char?): String =
        backingFile.readLines()[0].indices.joinToString("\n") { y ->
            backingFile.readLines().indices.joinToString("") { x ->
                (replace(Point2D(x, y)) ?: this.lines[y][x]).toString()
            }
        }

    operator fun get(point: Point2D): Char = this.lines[point.y.toInt()][point.x.toInt()]

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