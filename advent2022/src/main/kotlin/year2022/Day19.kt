package year2022

import adventday.AdventDay
import adventday.InputRepresentation

class Day19 : AdventDay(2022, 19) {
    enum class Miner(val bit: Int) {
        ORE(1),
        CLAY(2),
        OBSIDIAN(4),
        GEODE(8)
    }
    data class Stock(
        val oreMiners: Int = 1,
        val clayMiners: Int = 0,
        val obsidianMiners: Int = 0,
        val geodeMiners: Int = 0,
        val oresMined: Int = 0,
        val clayMined: Int = 0,
        val obsidianMined: Int = 0,
        val geodesMined: Int = 0
    ) {
        fun allPossibleNextStates(blueprint: Blueprint, canBuyNext: Int = 15): List<Pair<Stock, Int>> {
            val maxOreCost = listOf(blueprint.oreRobotCost, blueprint.clayRobotCost, blueprint.obsidianRobotCost.first, blueprint.geodeRobotCost.first).max()
            val maxClayCost = blueprint.obsidianRobotCost.second
            val maxObsidianCost = blueprint.geodeRobotCost.second
            if (oresMined / blueprint.geodeRobotCost.first > 0 && obsidianMined / blueprint.geodeRobotCost.second > 0) {
                return listOf(build(blueprint, Miner.GEODE) to 15)
            }
            val possibleBuilds = mutableListOf<Miner>()
            if (maxObsidianCost > obsidianMiners && canBuyNext and Miner.OBSIDIAN.bit != 0 &&
                oresMined / blueprint.obsidianRobotCost.first > 0 &&
                clayMined / blueprint.obsidianRobotCost.second > 0
            ) {
                possibleBuilds += Miner.OBSIDIAN
            }
            if (maxClayCost > clayMiners && oresMined / blueprint.clayRobotCost > 0 &&
                canBuyNext and Miner.CLAY.bit != 0) {
                possibleBuilds += Miner.CLAY
            }
            if (maxOreCost > oreMiners && oresMined / blueprint.oreRobotCost > 0 && canBuyNext and Miner.ORE.bit != 0) {
                possibleBuilds += Miner.ORE
            }
            val notAllowedToBuyAnymore = possibleBuilds.fold(canBuyNext) { cur, acc -> acc.bit xor cur }
            return listOf(build(blueprint) to notAllowedToBuyAnymore) + possibleBuilds.map { build(blueprint, it) to 15 }
        }

        private fun build(
            blueprint: Blueprint,
            miner: Miner? = null
        ): Stock = with(produce()) {
            when (miner) {
                null -> this
                Miner.ORE -> copy(
                    oreMiners = oreMiners + 1,
                    oresMined = oresMined - blueprint.oreRobotCost
                )
                Miner.CLAY -> copy(
                    clayMiners = clayMiners + 1,
                    oresMined = oresMined - blueprint.clayRobotCost
                )
                Miner.OBSIDIAN -> copy(
                    obsidianMiners = obsidianMiners + 1,
                    oresMined = oresMined - blueprint.obsidianRobotCost.first,
                    clayMined = clayMined - blueprint.obsidianRobotCost.second
                )
                Miner.GEODE -> copy(
                    geodeMiners = geodeMiners + 1,
                    oresMined = oresMined - blueprint.geodeRobotCost.first,
                    obsidianMined = obsidianMined - blueprint.geodeRobotCost.second
                )
            }
        }

        private fun produce() = copy(
            oresMined = oresMined + oreMiners,
            clayMined = clayMined + clayMiners,
            obsidianMined = obsidianMined + obsidianMiners,
            geodesMined = geodesMined + geodeMiners
        )

        fun maxMineGeodesIn(minutes: Int, blueprint: Blueprint, canBuyNext: Int = 15): Int {
            if (minutes == 0) return geodesMined
            val nextStates = allPossibleNextStates(blueprint, canBuyNext)
            return nextStates.maxOf { (it, canBuy) -> it.maxMineGeodesIn(minutes - 1, blueprint, canBuy) }
        }
    }

    data class Blueprint(
        val oreRobotCost: Int,
        val clayRobotCost: Int,
        val obsidianRobotCost: Pair<Int, Int>,
        val geodeRobotCost: Pair<Int, Int>
    ) {
        companion object {
            fun from(input: String): Blueprint {
                val (a, b, c, d) = input.split(". ")
                val oreRobotCost = a.split("costs ")[1].split(" ")[0].toInt()
                val clayRobotCost = b.split("costs ")[1].split(" ")[0].toInt()
                val obsidianRobotCost = c.split("costs ")[1].split(" ").let {
                    it[0].toInt() to it[3].toInt()
                }
                val geodeRobotCost = d.split("costs ")[1].split(" ").let {
                    it[0].toInt() to it[3].toInt()
                }

                return Blueprint(
                    oreRobotCost,
                    clayRobotCost,
                    obsidianRobotCost,
                    geodeRobotCost
                )
            }
        }
    }

    override fun part1(input: InputRepresentation): Int {
        val blueprints = input.lines.map { it: String -> Blueprint.from(it) }

        return blueprints.foldIndexed(0) { i, acc, cur ->
            val curRes = Stock().maxMineGeodesIn(24, cur)
            ((i + 1) * curRes) + acc
        }
    }
    override fun part2(input: InputRepresentation): Long {
        val blueprints = input.lines.take(3).map { Blueprint.from(it) }

        return blueprints.fold(1L) { acc, cur ->
            acc * Stock().maxMineGeodesIn(32, cur)
        }
    }
}

fun main() = Day19().run()