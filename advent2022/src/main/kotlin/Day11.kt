class Day11 : AdventDay(2022, 11) {
    data class Monkey(
        val monkeyNumber: Int,
        val items: List<Int>,
        val operation: (Long) -> Long,
        val throwToMonkey: (Int) -> Int
    )

    private fun List<String>.parseSingleMonkey(): Monkey {
        val monkeyNumber = first().split(" ")[1].split(":")[0].toInt()
        val items = get(1).split("  Starting items: ")[1].split(", ").map { it.toInt() }
        val (op, commands) = get(2).split("  Operation: ")[1]
            .split(" = ")
            .let { (_, b) ->
                val op: (Long, Long) -> Long = if (b.contains('*')) Long::times else Long::plus
                op to  b.split(" * ", " + ")
            }
        fun operation(old: Long): Long {
            val first = when {
                commands.first() == "old" -> old
                else -> commands.first().toLong()
            }
            val second = when {
                commands[1] == "old" -> old
                else -> commands[1].toLong()
            }
            return op(first, second)
        }
        val testLine = get(3).split("  Test: divisible by ")[1].toInt()
        val trueLine = get(4).split("    If true: throw to monkey ")[1].toInt()
        val falseLine = get(5).split("    If false: throw to monkey ")[1].toInt()
        fun throwTo(value: Int) =
            if (value % testLine == 0) {
                trueLine
            } else
                falseLine
        return Monkey(
            monkeyNumber,
            items,
            operation = ::operation,
            ::throwTo
        )
    }

    private fun Array<Monkey>.simulateFor(
        rounds: Int,
        adjustExpectation: (Long) -> Int
    ): IntArray {
        val monkeyInspects = IntArray(size) { 0 }
        for(round in 0 until rounds) {
            for (i in indices) {
                val monkey = get(i)
                monkey.items.forEachIndexed { _, oldValue ->
                    monkeyInspects[i] = monkeyInspects[i] + 1
                    val newValue = adjustExpectation(monkey.operation(oldValue.toLong()))
                    val newMonkey = monkey.throwToMonkey(newValue)
                    set(newMonkey, get(newMonkey).copy(items = get(newMonkey).items + newValue))
                }
                set(i, get(i).copy(items = listOf()))
            }
        }
        return monkeyInspects
    }

    override fun part1(input: List<String>): Int {
        val monkeys = input.joinToString("\n")
            .split("\n\n")
            .map { it.split("\n").parseSingleMonkey() }
            .toTypedArray()
        val monkeyInspects = monkeys.simulateFor(20) { i -> (i / 3).toInt() }

        val order = monkeyInspects.sortedByDescending { it }
        return order[0] * order[1]
    }

    override fun part2(input: List<String>): Long {
        val monkeys = input.joinToString("\n")
            .split("\n\n")
            .map { it.split("\n").parseSingleMonkey() }
            .toTypedArray()
        val divisors = input
            .filter { it.contains("  Test: divisible by ") }
            .map { it.split("  Test: divisible by ")[1].toInt() }
        val lcm = divisors.reduceRight(::lcm)
        val monkeyInspects = monkeys.simulateFor(10_000) { i -> (i % lcm).toInt() }

        val order = monkeyInspects.sortedByDescending { it }
        return order[0].toLong() * order[1]
    }
}

fun main() = Day11().run()