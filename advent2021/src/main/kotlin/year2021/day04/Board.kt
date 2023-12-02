package year2021.day04

data class Board(
    val row1: Row,
    val row2: Row,
    val row3: Row,
    val row4: Row,
    val row5: Row,
) {
    val rows
        get() = listOf(row1, row2, row3, row4, row5)
    val columns
        get() = (0..4).map { pos -> rows.map { it[pos] } }
    val cells
        get() = rows.flatMap { it.cells }.toSet()
    override fun toString() = """
        --------------------------
        $row1
        $row2
        $row3
        $row4
        $row5
        --------------------------
    """.trimIndent()
}

fun List<String>.toBoard(): Board = Board(
    this[0].trim().toRow(),
    this[1].trim().toRow(),
    this[2].trim().toRow(),
    this[3].trim().toRow(),
    this[4].trim().toRow(),
)