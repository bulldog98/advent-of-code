package year2021.day14

import year2021.day14.RuleApplyToFirstOccurrence.Companion.applyAllRules

data class SimulationState(
    val pairCounts: Map<String, Long>,
    val endChar: Char,
    val rules: List<Rule>,
    val afterStep: Int = 0
): () -> SimulationState {
    constructor(start: String, rules: List<Rule>) : this(
        start.windowed(2).groupingBy { it }.eachCount().mapValues { it.value.toLong() },
        start.last(),
        rules
    )
    constructor(input: Pair<List<String>, List<String>>) : this(input.first.single(), input.second.map(::Rule))

    override operator fun invoke() = copy(
        pairCounts = buildMap {
            pairCounts.forEach { (pair, count) ->
                val (firstPair, secondPair) = pair.applyAllRules(sequenceOf(rules.first { it(pair) })).windowed(2)
                this.compute(firstPair) { _, oldCount ->
                    (oldCount ?: 0L) + count
                }
                this.compute(secondPair) { _, oldCount ->
                    (oldCount ?: 0L) + count
                }
            }
        },
        afterStep = afterStep + 1
    )

    fun simulate(): Sequence<SimulationState> = sequence {
        val nextStep = this@SimulationState()
        yield(nextStep)
        yieldAll(nextStep.simulate())
    }

    fun computeDifference(): Long {
        val counts = pairCounts.keys.groupBy {
            it.first()
        }.mapValues {
            it.value.sumOf { pair -> pairCounts[pair]!!.toLong() }
        }.toMutableMap()
        counts.compute(endChar) { _, count -> (count ?: 0L) + 1L }
        return counts.values.max() - counts.values.min()
    }
}
