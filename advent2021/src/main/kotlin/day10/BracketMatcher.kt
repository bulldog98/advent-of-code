package day10

import java.util.*

sealed interface BracketMatcher {
    val errorScore: Int
    val completionScore: Int
    val opening: Char
    val closing: Char
}

private data object NormalBracketMatcher : BracketMatcher {
    override val errorScore: Int
        get() = 3
    override val completionScore: Int
        get() = 1
    override val opening: Char
        get() = '('
    override val closing: Char
        get() = ')'
}

private data object SquareBracketMatcher : BracketMatcher {
    override val errorScore: Int
        get() = 57
    override val completionScore: Int
        get() = 2
    override val opening: Char
        get() = '['
    override val closing: Char
        get() = ']'
}

private data object SquirrellyBracketMatcher : BracketMatcher {
    override val errorScore: Int
        get() = 1197
    override val completionScore: Int
        get() = 3
    override val opening: Char
        get() = '{'
    override val closing: Char
        get() = '}'
}

private data object TriangularBracketMatcher : BracketMatcher {
    override val errorScore: Int
        get() = 25137
    override val completionScore: Int
        get() = 4
    override val opening: Char
        get() = '<'
    override val closing: Char
        get() = '>'
}

val allMatchers: List<BracketMatcher> = listOf(NormalBracketMatcher, SquareBracketMatcher, SquirrellyBracketMatcher, TriangularBracketMatcher)

fun Char.toBracketMatcher(): BracketMatcher = allMatchers.first { it.opening == this || it.closing == this }

fun String.computeEndState(): Pair<BracketMatcher?, Stack<BracketMatcher>> {
    val stack: Stack<BracketMatcher> = Stack()
    stack.push(first().toBracketMatcher())
    drop(1).forEach {
        val matcher = it.toBracketMatcher()
        when (it) {
            matcher.opening -> { stack.push(matcher) }
            matcher.closing -> if (matcher == stack.peek()) {
                stack.pop()
            } else {
                return matcher to stack
            }
        }
    }
    return null to stack
}

fun Collection<BracketMatcher>.scoreCompletion(): Long =
    map {
        it.completionScore
    }.fold(0L) { total, next ->
        total * 5L + next
    }