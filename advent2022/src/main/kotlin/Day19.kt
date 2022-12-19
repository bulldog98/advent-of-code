class Day19 : AdventDay(2022, 19) {
    private enum class Miner {
        ORE,
        CLAY,
        OBSIDIAN,
        GEODE
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
        fun allPossibleNextStates(blueprint: Blueprint): List<Stock> {
            val maxOreCost = listOf(blueprint.oreRobotCost, blueprint.clayRobotCost, blueprint.obsidianRobotCost.first, blueprint.geodeRobotCost.first).max()
            val maxClayCost = blueprint.obsidianRobotCost.second
            val maxObsidianCost = blueprint.geodeRobotCost.second
            if (oresMined / blueprint.geodeRobotCost.first > 0 && obsidianMined / blueprint.geodeRobotCost.second > 0) {
                return listOf(build(blueprint, Miner.GEODE))
            }
            val possibleBuilds = mutableListOf<Miner>()
            if (maxObsidianCost > obsidianMiners &&
                oresMined / blueprint.obsidianRobotCost.first > 0 &&
                clayMined / blueprint.obsidianRobotCost.second > 0
            ) {
                possibleBuilds += Miner.OBSIDIAN
            }
            if (maxClayCost > clayMiners && oresMined / blueprint.clayRobotCost > 0) {
                possibleBuilds += Miner.CLAY
            }
            if (maxOreCost > oreMiners && oresMined / blueprint.oreRobotCost > 0) {
                possibleBuilds += Miner.ORE
            }
            return listOf(build(blueprint)) + possibleBuilds.map { build(blueprint, it) }
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

        fun maxMineGeodesIn(minutes: Int, blueprint: Blueprint): Int {
            if (minutes == 0) return geodesMined
            val nextStates = allPossibleNextStates(blueprint)
            if (nextStates.isEmpty()) {
                println(blueprint)
                println(minutes)
                println(this)
            }
            return nextStates.maxOf { it.maxMineGeodesIn(minutes - 1, blueprint) }
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

    override fun part1(input: List<String>): Int {
        val blueprints = input.map { Blueprint.from(it) }

        return blueprints.foldIndexed(0) { i, acc, cur ->
            val curRes = Stock().maxMineGeodesIn(24, cur)
            ((i + 1) * curRes) + acc
        }
    }
    override fun part2(input: List<String>): Long =
        TODO()
}

fun main() = Day19().run()