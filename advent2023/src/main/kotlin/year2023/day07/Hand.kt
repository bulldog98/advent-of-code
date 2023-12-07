package year2023.day07

data class Hand(val cards: String, val bid: Int, val type: HandType) {
    companion object {
        private fun String.computeTypeWithJoker(): HandType {
            val jokerCount = count { it == 'J' }
            val counts = filter { it != 'J' }.groupingBy { it }.eachCount().values
            return when {
                jokerCount == 0 -> computeType()
                jokerCount >= 4 -> HandType.FiveOfAKind
                jokerCount == 3 && 2 in counts -> HandType.FiveOfAKind
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