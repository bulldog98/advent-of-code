import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day19Test {
    private val day = Day19()
    @Nested
    inner class StockTest {
        private val blueprint = Day19.Blueprint(
            oreRobotCost = 1,
            clayRobotCost = 2,
            obsidianRobotCost = 3 to 1,
            geodeRobotCost = 4 to 1
        )
        @Test
        fun `tries to buy ore miner`() {
            val input = Day19.Stock(oreMiners = 0, oresMined = 1)
            val res = input.allPossibleNextStates(blueprint)

            assertEquals(listOf(input, Day19.Stock()), res)
        }
        @Nested
        inner class Example1 {
            private val exampleBlueprint1 = Day19.Blueprint(
                oreRobotCost = 4,
                clayRobotCost = 2,
                obsidianRobotCost = 3 to 14,
                geodeRobotCost = 2 to 7
            )
            @Test
            fun `correctly makes first step`() {
                val res = Day19.Stock().allPossibleNextStates(exampleBlueprint1)

                assertEquals(listOf(Day19.Stock(oresMined = 1)), res)
            }

            @Test
            fun `correctly makes third step`() {
                val res = Day19.Stock(oresMined = 2).allPossibleNextStates(exampleBlueprint1)

                assertTrue(Day19.Stock(oresMined = 1, clayMiners = 1) in res)
            }

            @Test
            fun `correctly makes 6 to 7 step`() {
                val res = Day19.Stock(oresMined = 2, clayMiners = 2, clayMined = 4).allPossibleNextStates(exampleBlueprint1)

                assertTrue(Day19.Stock(
                    oresMined = 1,
                    clayMined = 6,
                    clayMiners = 3
                ) in res)
            }

            @Test
            fun `correctly makes 9 to 10 step`() {
                val startingState = Day19.Stock(
                    oresMined = 3,
                    clayMined = 12,
                    clayMiners = 3
                )
                val res = startingState.allPossibleNextStates(exampleBlueprint1)

                assertTrue(startingState.copy(
                    oresMined = 4,
                    clayMined = 15
                ) in res)
            }

            @Test
            fun `correctly makes 10 to 11 step`() {
                val startingState = Day19.Stock(
                    oresMined = 4,
                    clayMined = 15,
                    clayMiners = 3
                )
                val res = startingState.allPossibleNextStates(exampleBlueprint1)

                assertTrue(startingState.copy(
                    oresMined = 2,
                    clayMined = 4,
                    obsidianMiners = 1
                ) in res)
            }
        }
        @Nested
        inner class Example2 {
            private val exampleBlueprint2 = Day19.Blueprint(
                oreRobotCost = 2,
                clayRobotCost = 3,
                obsidianRobotCost = 3 to 8,
                geodeRobotCost = 3 to 12
            )
            @Test
            fun `minute 3 to 4`() {
                val stock = Day19.Stock(oresMined = 2)
                val res = stock.allPossibleNextStates(exampleBlueprint2)

                assertEquals(
                    listOf(
                        Day19.Stock(oresMined = 3),
                        Day19.Stock(oresMined = 1, oreMiners = 2)
                    ),
                    res
                )
            }
        }
    }

    @Test
    fun part1() = assertEquals(33, day.testPart1())

    @Test
    fun part2() = assertEquals(56L*62L, day.testPart2())
}