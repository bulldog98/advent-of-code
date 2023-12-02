package year2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day10Test {
    private val day = Day10()

    @Test
    fun part1() = assertEquals(13140, day.testPart1())


    @Test
    fun part2() = assertEquals(
        """
            ##..##..##..##..##..##..##..##..##..##..
            ###...###...###...###...###...###...###.
            ####....####....####....####....####....
            #####.....#####.....#####.....#####.....
            ######......######......######......####
            #######.......#######.......#######.....
        """.trimIndent()
        ,
        day.testPart2()
    )
}