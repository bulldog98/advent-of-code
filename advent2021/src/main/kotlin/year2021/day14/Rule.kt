package year2021.day14

data class Rule(val first: Char, val second: Char, val insert: Char): (String) -> Boolean {
    val prefix by lazy {
        "$first$second"
    }

    private val lazyPrefixRegex by lazy {
        prefix.toRegex()
    }

    private constructor(prefix: String, insert: Char) : this(prefix[0], prefix[1], insert)

    constructor(input: String):
        this(
            input.substringBefore(" ->"),
            input.substringAfter("-> ")[0]
        )

    override fun invoke(p1: String): Boolean = lazyPrefixRegex.containsMatchIn(p1)

    fun matchesAtPositions(input: String): Sequence<Int> = when {
        first == second && lazyPrefixRegex.containsMatchIn(input) -> input
            .windowed(2)
            .asSequence()
            .withIndex()
            .filter { it.value == prefix }
            .map { it.index }
        else -> lazyPrefixRegex.findAll(input).map { it.range.first }
    }

    override fun toString() = "$first$second -> $insert"
}
