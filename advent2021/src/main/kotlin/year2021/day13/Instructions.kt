package year2021.day13

import Point2D

data class Instructions(
    val currentFolding: FoldedInstruction,
    val folds: List<FoldInstruction>
) {
    private constructor(dots: Set<Point2D>, folds: List<FoldInstruction>): this(FoldedInstruction(dots), folds)

    fun folded() =
        folds
            .asSequence()
            .runningFold(currentFolding) { cur, foldingRule ->
                foldingRule.useOn(cur)
            }

    companion object {
        operator fun invoke(input: Pair<List<String>, List<String>>) = Instructions(
            dots = input
                .first
                .map {
                    val (x, y) = it.split(",")
                    Point2D(x.toInt(), y.toInt())
                }.toSet(),
            folds = input
                .second
                .map(::FoldInstruction)
        )
    }
}
