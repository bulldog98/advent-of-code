sealed interface Instruction
object Ls : Instruction {
    override fun toString(): String = "Ls"
}
object Up : Instruction {
    override fun toString(): String = "Up"
}
data class Cd(val dir: String): Instruction
data class DirOutput(val name: String): Instruction
data class FileOutput(val name: String, val size: Int): Instruction


fun String.toInstruction() = when {
    startsWith("$ cd ") -> {
        val loc = substringAfter("$ cd ")
        if (loc == "..") {
            Up
        } else
            Cd(loc)
    }
    this == "$ ls" -> Ls
    startsWith("dir ") -> DirOutput(substringAfter("dir "))
    this[0].isDigit() -> {
        val (a, b) = split(" ")
        FileOutput(b, a.toInt())
    }
    else -> error("Can't parse: $this")
}
fun List<String>.toInstructions() = map {it.toInstruction() }

fun List<Instruction>.buildFileTree(): Directory {
    val root = Directory(null)
    var current = root
    forEach {
        current = when (it) {
            is Up -> current.parent!!
            Cd("/"), is Ls -> current
            is Cd -> current[it.dir]!!
            is DirOutput -> current.apply {
                this[it.name] = Directory(current)
            }
            is FileOutput -> {
                current.totalSize += it.size
                current
            }
        }
    }
    root.updateTotalSizes()
    return root
}

class Directory(
    val parent: Directory?,
    private val childDirs: MutableMap<String, Directory> = mutableMapOf()
): MutableMap<String, Directory> by childDirs {
    var totalSize: Int = 0

    fun find(predicate: (Directory) -> Boolean): List<Directory> =
        if (predicate(this)) {
            listOf(this)
        } else {
            emptyList()
        } +
        flatMap { (_, n) -> n.find(predicate) }

    fun updateTotalSizes(): Int {
        totalSize += childDirs.values.sumOf { it.updateTotalSizes() }
        return totalSize
    }
}

class Day07 : AdventDay<Int>(2022, 7) {
    override fun part1(input: List<String>): Int = input
        .toInstructions()
        .also { it.forEach { l -> println(l) } }
        .buildFileTree()
        .find { it.totalSize <= 100000 }
        .sumOf { it.totalSize }
    override fun part2(input: List<String>): Int {
        val root = input.toInstructions().buildFileTree()
        val excess = 30000000 - (70000000 - root.totalSize)
        return root.find { it.totalSize >= excess }
            .minBy { it.totalSize }
            .totalSize
    }
}


fun main() = Day07().run()