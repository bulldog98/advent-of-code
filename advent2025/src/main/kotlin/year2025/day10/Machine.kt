package year2025.day10

import helper.numbers.toAllLongs

data class Machine(
    val lightIndicators: List<Boolean>,
    val buttons: List<Set<Long>>,
    @Suppress("SpellCheckingInspection")
    val joltageRequirements: List<Long>,
) {
    companion object {
        fun parse(line: String): Machine {
            val split = line.split(" ")
            return Machine(
                split.first().drop(1).dropLast(1).map {
                    when (it) {
                        '.' -> false
                        '#' -> true
                        else -> error("unable to parse $it")
                    }
                },
                split.drop(1).dropLast(1).map { it.toAllLongs().toSet() },
                split.last().toAllLongs().toList()
            )
        }
    }
}