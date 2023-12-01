package day08

class Encoder(input: String) {
    private val wires = input.split(" |")[0].split(" ").map { it.toSet() }
    private val output = input.split(" |")[1].split(" ")
    // "cf"
    private val encoded1 by lazy {
        wires.find { it.size == 2 } ?: throw Error("no 1 encoded")
    }
    // "acdeg"
    private val encoded2 by lazy {
        wires.filter { it.size == 5 }.find { (it - a - encoded4).size == 2 } ?: throw Error("no 2 encoded")
    }
    // "acdfg"
    private val encoded3 by lazy {
        wires.filter { it.size == 5 }.find { (it - encoded1).size == 3 } ?: throw Error("no 3 encoded")
    }
    // "bcdf"
    private val encoded4 by lazy {
        wires.find { it.size == 4 } ?: throw Error("no 4 encoded")
    }
    private val encoded5 by lazy {
        wires.filter { it.size == 5 }.find { it != encoded2 && it != encoded3 } ?: throw Error("no 5 encoded")
    }
    private val encoded6 by lazy {
        wires.filter { it.size == 6 }.find { (it - encoded1).size == 5 } ?: throw Error("no 6 encoded")
    }
    // "acf"
    private val encoded7 by lazy {
        wires.find { it.size == 3 } ?: throw Error("no 7 encoded")
    }
    private val encoded8 by lazy {
        wires.find { it.size == 7 } ?: throw Error("no 8 encoded")
    }
    private val encoded9 by lazy {
        wires.filter { it.size == 6 }.find { (it - encoded4).size == 2 } ?: throw Error("no 9 encoded")
    }
    private val a by lazy {
        encoded7.find { it !in encoded1 }
    }

    private fun parse(str: String): Int = when(str.toSet()) {
        encoded1 -> 1
        encoded2 -> 2
        encoded3 -> 3
        encoded4 -> 4
        encoded5 -> 5
        encoded6 -> 6
        encoded7 -> 7
        encoded8 -> 8
        encoded9 -> 9
        else -> 0
    }

    val value: Int
        get() = output.joinToString("") { parse(it).toString() }.toInt()
}