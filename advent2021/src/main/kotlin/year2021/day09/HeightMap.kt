package year2021.day09

fun Pair<Int, Int>.neighbors(maxX: Int, maxY: Int) =
    listOf(
        first - 1 to second,
        first + 1 to second,
        first to second - 1,
        first to second + 1
    ).filter { (x, y) ->
        x >= 0 && y >= 0 && x < maxX && y < maxY
    }

fun Pair<Int, Int>.computeBasin(map: HeightMap): Set<Pair<Int, Int>> {
    val basin = mutableSetOf(this)
    val workList = mutableListOf(this)
    while (workList.isNotEmpty()) {
        val current = workList.removeAt(0)
        val neighbors = current.neighbors(map.maxX, map.maxY).filter { map[it] != 9 }
        workList.addAll(neighbors.filter { it !in basin })
        basin += neighbors
    }
    return basin
}

class HeightMap(input: List<String>) {
    private val area: Array<Array<Int>> =
        input.map {
            it.map(Char::digitToInt).toTypedArray()
        }.toTypedArray()

    operator fun get(x: Pair<Int, Int>): Int =
        area[x.first].let { it[x.second] }

    val maxX = area.size
    val maxY = area[0].size

    val lowPoints
        get() = (0 until maxX)
            .flatMap { x ->
                (0 until maxY).map { y ->
                    x to y
                }
            }
            .filter { it.neighbors(maxX, maxY)
            .all { neighbor -> this[it] < this[neighbor] } }
}