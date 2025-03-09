package year2021.day18

import kotlin.math.max

sealed interface SnailFishNumber {
    fun canExplode(): Boolean = depth() > 4
    fun canSplit(): Boolean
    fun depth(): Int
    operator fun plus(other: SnailFishNumber): SnailFishNumber = SnailFishPairNumber(this, other)
        .reduce()

    fun reduce(): SnailFishNumber
    fun addToLeftMost(adjustment: Int): SnailFishNumber
    fun addToRightMost(adjustment: Int): SnailFishNumber
    fun computeMagnitude(): Int

    private data class SnailFishLiteralNumber(
        val number: Int,
    ) : SnailFishNumber {
        override fun toString(): String = "$number"
        override fun depth() = 0
        override fun reduce(): SnailFishNumber = this
        override fun canSplit(): Boolean = number > 9
        override fun addToLeftMost(adjustment: Int) = copy(number = number + adjustment)
        override fun addToRightMost(adjustment: Int) = copy(number = number + adjustment)
        override fun computeMagnitude(): Int = number
    }

    private data class SnailFishPairNumber(
        val left: SnailFishNumber,
        val right: SnailFishNumber,
    ) : SnailFishNumber {
        override fun toString(): String = "[$left,$right]"
        override fun depth(): Int = 1 + max(left.depth(), right.depth())
        override fun reduce(): SnailFishNumber = generateSequence(this) { oldNumber ->
            when {
                oldNumber.canExplode() -> {
                    val (leftValue, rightValue) = oldNumber.toExplode() ?: error("could not find pair to explode")
                    val explosionPath = oldNumber.findExplodePath()
                    val replacedWith0 = oldNumber.modifyWithPath(explosionPath) { SnailFishLiteralNumber(0) }
                    val leftReplaced = if (explosionPath.any { d -> d == Way.RIGHT })
                        replacedWith0.modifyWithPath(explosionPath.dropLastWhile { d -> d == Way.LEFT }.dropLast(1) + Way.LEFT) {
                            it.addToRightMost((leftValue as SnailFishLiteralNumber).number)
                        }
                    else
                        replacedWith0
                    val rightReplaced = if (explosionPath.any { d -> d == Way.LEFT })
                        leftReplaced.modifyWithPath(explosionPath.dropLastWhile { d -> d == Way.RIGHT }.dropLast(1) + Way.RIGHT) {
                            it.addToLeftMost((rightValue as SnailFishLiteralNumber).number)
                        }
                    else
                        leftReplaced
                    rightReplaced
                }
                oldNumber.canSplit() -> oldNumber.modifyWithPath(oldNumber.findSplitPath()) {
                    (it as SnailFishLiteralNumber).number.split()
                }
                else -> oldNumber
            }
        }.zipWithNext()
            .first {
                // println(it.first)
                it.first == it.second
            }
            .first
        override fun canSplit(): Boolean = left.canSplit() || right.canSplit()
        override fun addToLeftMost(adjustment: Int) = copy(left = left.addToLeftMost(adjustment))
        override fun addToRightMost(adjustment: Int) = copy(right = right.addToRightMost(adjustment))
        override fun computeMagnitude(): Int = left.computeMagnitude() * 3 + right.computeMagnitude() * 2

        private fun findSplitPath(currentPath: List<Way> = emptyList()): List<Way> = when {
            left.canSplit() && left is SnailFishLiteralNumber -> currentPath + Way.LEFT
            left.canSplit() -> (left as SnailFishPairNumber).findSplitPath(currentPath + Way.LEFT)
            right.canSplit() && right is SnailFishLiteralNumber -> currentPath + Way.RIGHT
            right.canSplit() -> (right as SnailFishPairNumber).findSplitPath(currentPath + Way.RIGHT)
            else -> error("should not happen")
        }
        private fun findExplodePath(currentPath: List<Way> = emptyList()): List<Way> = when {
            left is SnailFishPairNumber && right is SnailFishPairNumber -> {
                val leftTry = left.findExplodePath(currentPath + Way.LEFT)
                leftTry.ifEmpty { right.findExplodePath(currentPath + Way.RIGHT) }
            }
            left is SnailFishPairNumber -> left.findExplodePath(currentPath + Way.LEFT)
            right is SnailFishPairNumber -> right.findExplodePath(currentPath + Way.RIGHT)
            currentPath.size == 4 -> currentPath
            else -> emptyList()
        }
        private fun toExplode(depth: Int = 0): SnailFishPairNumber? = when {
            depth == 4 -> this
            left is SnailFishPairNumber && right is SnailFishPairNumber ->
                left.toExplode(depth + 1) ?: right.toExplode(depth + 1)
            left is SnailFishPairNumber -> left.toExplode(depth + 1)
            right is SnailFishPairNumber -> right.toExplode(depth + 1)
            else -> null
        }
        private fun modifyWithPath(
            path: List<Way>,
            modify: (SnailFishNumber) -> SnailFishNumber
        ): SnailFishPairNumber = when (path.size) {
            1 -> path.single().modify(this, modify)
            else -> {
                val child = path.first()(this) as SnailFishPairNumber
                path.first().modify(this, child.modifyWithPath(path.drop(1), modify))
            }
        }
    }

    companion object {
        fun parse(input: String): SnailFishNumber = parseHelper(input).let {
            assert(it.second.isEmpty()) { "should not leave rest for parsing ${it.second}" }
            it.first
        }

        private fun parseHelper(input: String): Pair<SnailFishNumber, String> = if (input.first().isDigit())
            SnailFishLiteralNumber(input.takeWhile { it.isDigit() }.toInt()) to input.dropWhile { it.isDigit() }
        else {
            assert(input.first() == '[') { ", separates snail numbers" }
            val (left, r1) = parseHelper(input.drop(1))
            assert(r1.first() == ',') { ", separates snail numbers" }
            val (right, r2) = parseHelper(r1.drop(1))
            assert(r2.first() == ']') { ", separates snail numbers" }
            SnailFishPairNumber(left, right) to r2.drop(1)
        }

        private fun Int.split() = when {
            this < 10 -> error("cannot split $this")
            else -> SnailFishPairNumber(
                SnailFishLiteralNumber(this / 2),
                SnailFishLiteralNumber(this / 2 + this % 2)
            )
        }

        private enum class Way(
            val selection: (SnailFishPairNumber) -> SnailFishNumber
        ) : (SnailFishPairNumber) -> SnailFishNumber by selection {
            LEFT(SnailFishPairNumber::left),
            RIGHT(SnailFishPairNumber::right);

            fun modify(snailFishPairNumber: SnailFishPairNumber, replaceWith: SnailFishNumber) = modify(snailFishPairNumber) { replaceWith }
            fun modify(snailFishPairNumber: SnailFishPairNumber, replaceWith: (SnailFishNumber) -> SnailFishNumber) =
                snailFishPairNumber.copy(
                    left = if (this == LEFT) replaceWith(snailFishPairNumber.left) else snailFishPairNumber.left,
                    right = if (this == RIGHT) replaceWith(snailFishPairNumber.right) else snailFishPairNumber.right,
                )
        }
    }
}