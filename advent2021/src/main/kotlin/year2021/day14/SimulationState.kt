package year2021.day14

import year2021.day14.RuleApplyToFirstOccurrence.Companion.applyAllRules

data class SimulationState(
    val start: String,
    val rules: List<Rule>,
    val afterStep: Int = 0
): () -> SimulationState, CharSequence by start {
    constructor(input: List<String>) : this(input[0], input.drop(2).map(::Rule))

    override operator fun invoke() = copy(
        start = rules
            .filter { it(start) }
            .fold(emptySequence<Pair<Int, Rule>>()) { acc, rule ->
                acc + rule.matchesAtPositions(start).map { it to rule }
            }.let { matches ->
                start.applyAllRules(
                    matches
                        .sortedBy { it.first }
                        .map { it.second }
                )
            },
        afterStep = afterStep + 1
    )

    fun simulate(): Sequence<SimulationState> = sequence {
        val nextStep = this@SimulationState()
        yield(nextStep)
        yieldAll(nextStep.simulate())
    }
}
