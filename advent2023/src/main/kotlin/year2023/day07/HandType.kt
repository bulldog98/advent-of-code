package year2023.day07

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