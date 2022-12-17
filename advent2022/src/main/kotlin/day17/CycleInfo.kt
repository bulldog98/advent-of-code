package day17

data class CycleInfo(
    val cycleHeight: Long,
    val cycleLength: Int,
    val cycleStartHeight: Int,
    val partialHeights: List<Long>
) {
    companion object {
        fun findFor(chamberOrig: Chamber): CycleInfo {
            val windCycleLength = chamberOrig.dirs.size
            val shapeCycleLength = chamberOrig.shapes.size
            val seenCycles = mutableSetOf<Int>()
            val cycleSyncNum = mutableSetOf<Int>()

            val partialCycleHeights = mutableListOf<Long>()
            var originalCoordinate: Coordinate? = null
            var originalShapePhase = -1

            var shapePhase = 0
            var time = 0
            var chamber = chamberOrig

            // just run it for 100_000 steps, hope to find a cycle
            for (step  in 0 until 100_000) {
                // this is the actual simulation
                chamber = chamber.simulateOneStep(step).also {
                    time += it.currentDirIndex - chamber.currentDirIndex
                }

                if (shapePhase % chamber.shapes.size == (shapeCycleLength - 1)) {
                    // Here we are in the case where we have cycle data
                    if (cycleSyncNum.isNotEmpty() && time % windCycleLength == cycleSyncNum.first()) {
                        val cycleHeight = chamber.lastShapeRestOn!!.y - originalCoordinate!!.y
                        val cycleLength = shapePhase - originalShapePhase

                        return CycleInfo(
                            cycleHeight,
                            cycleLength,
                            originalShapePhase,
                            partialHeights = partialCycleHeights.toList()
                        )
                    } else if (cycleSyncNum.isEmpty()) {
                        val windPhase = time % windCycleLength
                        if (!seenCycles.add(windPhase)) {
                            cycleSyncNum.add(windPhase)
                            originalCoordinate = chamber.lastShapeRestOn!!.copy()
                            originalShapePhase = shapePhase

                            println("Catching cycle $windPhase")
                        }
                    }
                }

                if (originalCoordinate != null) {
                    partialCycleHeights.add(chamber.rocks.maxOf { it.y })
                }

                shapePhase += 1
            }

            error("was unable to find cycle in 100_000 steps")
        }
    }
}