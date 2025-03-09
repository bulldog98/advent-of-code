package year2021.day16

enum class OperatorFunction(val typeID: Int, private val computation: List<Packet>.() -> Long): (List<Packet>) -> Long by computation {
    SUM(0, { sumOf { it.value } }),
    PRODUCT(1, {
        fold(1L) { acc, packet ->
            acc * packet.value
        }
    }),
    MIN(2, { minOf { it.value } }),
    MAX(3, { maxOf { it.value } }),
    GREATER_THAN(5, { if (this[0].value > this[1].value) 1 else 0 }),
    LESS_THAN(6, { if (this[0].value < this[1].value) 1 else 0 }),
    EQUAL(7, { if (this[0].value == this[1].value) 1 else 0 }),
}