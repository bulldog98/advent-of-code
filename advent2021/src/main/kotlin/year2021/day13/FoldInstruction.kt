package year2021.day13

import Point2D

sealed interface FoldInstruction {
    fun useOn(currentFolding: FoldedInstruction): FoldedInstruction

    data class XFoldInstruction(val x: Int): FoldInstruction {
        private fun Point2D.foldOnXLine(xLine: Int) = copy(
            x = if (x < xLine) x else xLine - (x - xLine)
        )

        override fun useOn(currentFolding: FoldedInstruction) =
            currentFolding.copy(
                points = currentFolding
                    .points
                    .map {
                        it.foldOnXLine(x)
                    }.toSet()
            )
    }
    data class YFoldInstruction(val y: Int): FoldInstruction {
        private fun Point2D.foldOnYLine(yLine: Int) = copy(
            y = if (y < yLine) y else yLine - (y - yLine)
        )

        override fun useOn(currentFolding: FoldedInstruction) =
            currentFolding.copy(
                points = currentFolding
                    .points
                    .map {
                        it.foldOnYLine(y)
                    }.toSet()
            )
    }
}

fun FoldInstruction(inputLine: String) = when {
    inputLine.startsWith("fold along x=") -> FoldInstruction.XFoldInstruction(inputLine.drop(13).toInt())
    inputLine.startsWith("fold along y=") -> FoldInstruction.YFoldInstruction(inputLine.drop(13).toInt())
    else -> error("cannot parse fold instruction")
}