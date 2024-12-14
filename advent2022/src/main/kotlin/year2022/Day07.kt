package year2022

import adventday.AdventDay

sealed interface Node {
    val name: String
    val parent: Node?
    val fileSize: Int
}

class File(override val fileSize: Int, override val name: String, override val parent: Node?): Node
class Directory(
    override val name: String,
    override val parent: Node?,
    val children: MutableList<Node> = mutableListOf()
): Node {
    override val fileSize: Int
        get() = children.sumOf { it.fileSize }

    fun filterInTree(pre: (Directory) -> Boolean): List<Directory> =
        children.filterIsInstance<Directory>()
            .flatMap { it.filterInTree(pre) } +
                if (pre(this))
                    listOf(this)
                else
                    emptyList()
}

fun List<String>.computeFolderContent(parent: Node): List<Node> = drop(1).map {
    val (a, b) = it.split(" ")
    when (a) {
        "dir" -> Directory(b, parent)
        else -> File(a.toInt(), b, parent)
    }
}

fun List<String>.parseInput(): Directory {
    val realInput = drop(1).joinToString("\n").split("$ ")
    var cur = Directory("/", null)
    val root = cur
    for (i in realInput.drop(1).map { it.lines().filter { l -> l.isNotBlank() } }) {
        when {
            i.first().startsWith("ls") -> {
                val children = i.computeFolderContent(cur)
                cur.children.addAll(children)
            }
            else -> {
                val (_, dir) = i.first().split(" ")
                cur = when (dir) {
                    ".." -> cur.parent as Directory
                    "/" -> root
                    else -> cur.children.find { it.name == dir } as? Directory ?: error(dir)
                }
            }
        }
    }
    return root
}

class Day07 : AdventDay(2022, 7) {
    override fun part1(input: List<String>): Int {
        val root = input.parseInput()
        return root.filterInTree { it.fileSize <= 100_000 }.sumOf { it.fileSize }
    }
    override fun part2(input: List<String>): Int {
        val root = input.parseInput()
        val currentSize = root.fileSize
        return root.filterInTree {
            70_000_000 - (currentSize - it.fileSize)  >= 30_000_000
        }.minOf { it.fileSize }
    }
}


fun main() = Day07().run()