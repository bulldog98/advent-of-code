package year2023

import AdventDay

fun Char.cardTypeValue(jokerBad: Boolean = false) = when (this) {
    '2' -> 'b'
    '3' -> 'c'
    '4' -> 'd'
    '5' -> 'e'
    '6' -> 'f'
    '7' -> 'g'
    '8' -> 'h'
    '9' -> 'i'
    'T' -> 'j'
    'J' -> if (jokerBad) 'a' else 'k'
    'Q' -> 'l'
    'K' -> 'm'
    'A' -> 'n'
    else -> error("can not happen")
}

fun String.asCardTypeValues(jokerBad: Boolean = false) =
    map { c -> c.cardTypeValue(jokerBad) }.joinToString("")

sealed interface HandType: Comparable<HandType> {
    val orderNumber: Int
    override operator fun compareTo(other: HandType): Int =
        compareBy<HandType> { it.orderNumber }.compare(this, other)
    data object FiveOfAKind: HandType {
        override val orderNumber: Int = 6
    }
    data object FourOfAKind: HandType {
        override val orderNumber: Int = 5
    }
    data object FullHouse: HandType {
        override val orderNumber: Int = 4
    }
    data object ThreeOfAKind: HandType {
        override val orderNumber: Int = 3
    }
    data object TwoPair: HandType {
        override val orderNumber: Int = 2
    }
    data object OnePair: HandType {
        override val orderNumber: Int = 1
    }
    data object HighCard: HandType {
        override val orderNumber: Int = 0
    }
}

data class Hand(val cards: String, val bid: Int, val type: HandType) {
    companion object {
        private fun String.computeTypeWithJoker(): HandType {
            val jokerCount = count { it == 'J' }
            val counts = filter { it != 'J' }.groupingBy { it }.eachCount().values
            return when {
                jokerCount == 0 -> computeType()
                jokerCount >= 4 -> HandType.FiveOfAKind
                jokerCount == 3 && 2 in counts ->  HandType.FiveOfAKind
                jokerCount == 3 -> HandType.FourOfAKind
                jokerCount == 2 -> when {
                    3 in counts -> HandType.FiveOfAKind
                    2 in counts -> HandType.FourOfAKind
                    else -> HandType.ThreeOfAKind
                }
                jokerCount == 1 -> when {
                    4 in counts -> HandType.FiveOfAKind
                    3 in counts -> HandType.FourOfAKind
                    counts.count { it == 2 } == 2 -> HandType.FullHouse
                    2 in counts -> HandType.ThreeOfAKind
                    else -> HandType.OnePair
                }
                else -> HandType.HighCard
            }
        }
        private fun String.computeType(): HandType {
            val counts = groupingBy { it }.eachCount().values
            return when {
                5 in counts -> HandType.FiveOfAKind
                4 in counts -> HandType.FourOfAKind
                3 in counts && 2 in counts -> HandType.FullHouse
                3 in counts -> HandType.ThreeOfAKind
                counts.count { it == 2 } == 2 -> HandType.TwoPair
                2 in counts -> HandType.OnePair
                else -> HandType.HighCard
            }
        }
        operator fun invoke(input: List<String>, jokersViewed: Boolean = false) = Hand(
            input[0],
            input[1].toInt(),
            if (jokersViewed)
                input[0].computeTypeWithJoker()
            else
                input[0].computeType()
        )
    }
}

object Day07 : AdventDay(2023, 7) {
    override fun part1(input: List<String>): Int =
        input
            .asSequence()
            .map {
                Hand(it.split(" "))
            }
            .sortedWith(compareBy({ it.type }, { it.cards.asCardTypeValues() }))
            .mapIndexed { i, it -> it to i + 1 }
            .sumOf { it.first.bid * it.second }

    override fun part2(input: List<String>): Any =
        input
            .asSequence()
            .map {
                Hand(it.split(" "), true)
            }
            .sortedWith(compareBy({ it.type }, { it.cards.asCardTypeValues(true) }))
            .mapIndexed { i, it -> it to i + 1 }
            .also { it.forEach(::println) }
            .sumOf { it.first.bid * it.second }
}

fun main() = Day07.run()
