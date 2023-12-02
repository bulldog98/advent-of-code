package year2021.day04

data class Row(
    val cell1: Int,
    val cell2: Int,
    val cell3: Int,
    val cell4: Int,
    val cell5: Int
) {
    val cells
        get() = listOf(cell1, cell2, cell3, cell4, cell5)
    operator fun get(index: Int) = when(index) {
        0 -> cell1
        1 -> cell2
        2 -> cell3
        3 -> cell4
        4 -> cell5
        else -> throw Error("illegal index")
    }

    override fun toString(): String = """|${cells.joinToString("\t")}|"""
}

fun String.toRow(): Row {
    val (row1, row2, row3, row4, row5) = split(' ').filter { it.isNotBlank() }.map { it.toInt() }
    return Row(
        row1,
        row2,
        row3,
        row4,
        row5
    )
}
