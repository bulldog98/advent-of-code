package year2021.day14

class RuleApplyToFirstOccurrence private constructor(
    private val rule: Rule
): (String, String) -> Pair<String, String> {
    override fun invoke(prefix: String, postfix: String): Pair<String, String> =
        "$prefix${postfix.substringBefore(rule.prefix)}${rule.first}${rule.insert}" to
                "${rule.second}${postfix.substringAfter(rule.prefix)}"

    companion object {
        private fun String.applyAllRulesHelper(rules: Sequence<RuleApplyToFirstOccurrence>) =
            rules
                .fold("" to this) { (prefix, postfix), rule ->
                    rule(prefix, postfix)
                }.let { (prefix, rest) ->
                    prefix + rest
                }

        fun String.applyAllRules(rules: Sequence<Rule>) =
            applyAllRulesHelper(rules.map(::RuleApplyToFirstOccurrence))
    }
}
