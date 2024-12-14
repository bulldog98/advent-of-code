package year2022

import adventday.AdventDay
import adventday.InputRepresentation

typealias Forest = List<List<Int>>

fun Forest.isVisible(x: Int, y: Int): Boolean {
    val ownHeight = this[x][y]
    val leftVisible = (0 until x).all { this[it][y] < ownHeight }
    val rightVisible = (x + 1 until  this[x].size).all { this[it][y] < ownHeight }
    val topVisible = (0 until  y).all { this[x][it] < ownHeight }
    val bottomVisible = (y + 1 until this.size).all { this[x][it] < ownHeight }
    return leftVisible || rightVisible || topVisible || bottomVisible ||
            x == 0 || y == 0 || x == this[y].size - 1 || y == this.size - 1
}

fun List<String>.parseForest(): Forest = map { it.toList().map { c -> c.toString().toInt() }}

fun Iterable<Int>.countUnless(pred: (Int) -> Boolean): Int {
    var res = 0
    for (element in this) {
        res++
        if (pred(element))
            return res
    }
    return res
}

fun Forest.getVisibleCoords(): Set<Pair<Int, Int>> {
    val foundInvisible = mutableSetOf<Pair<Int, Int>>()
    for (x in this.indices) {
        for (y in this[x].indices) {
            if (isVisible(x, y)) {
                foundInvisible.add(x to y)
            }
        }
    }
    return foundInvisible
}
fun Forest.sceniceMeasure(x: Int, y: Int): Int {
    val ownHeight = this[x][y]
    val leftVisible = (0 until x).reversed().countUnless { this[it][y] >= ownHeight }
    val rightVisible = (x + 1 until  this[x].size).countUnless { this[it][y] >= ownHeight }
    val topVisible = (0 until  y).reversed().countUnless { this[x][it] >= ownHeight }
    val bottomVisible = (y + 1 until this.size).countUnless { this[x][it] >= ownHeight }
    return leftVisible * rightVisible * topVisible * bottomVisible
}

class Day08 : AdventDay(2022, 8) {
    override fun part1(input: InputRepresentation): Int {
        return input.parseForest().getVisibleCoords().size
    }
    override fun part2(input: InputRepresentation): Int {
        val forest = input.parseForest()
        val visible = forest.getVisibleCoords().maxBy { (x,y) -> forest.sceniceMeasure(x, y)}
        return forest.sceniceMeasure(visible.first, visible.second)
    }
}


fun main() = Day08().run()