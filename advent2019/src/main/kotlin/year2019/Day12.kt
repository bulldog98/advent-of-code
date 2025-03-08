package year2019

import Point3D
import adventday.AdventDay
import adventday.InputRepresentation
import helper.numbers.toAllLongs
import kotlin.math.absoluteValue
import kotlin.math.sign

data class Planet(val position: Point3D, val velocity: Point3D) {
    fun computeEnergy(): Long =
        (position.x.absoluteValue + position.y.absoluteValue + position.z.absoluteValue) *
            (velocity.x.absoluteValue + velocity.y.absoluteValue + velocity.z.absoluteValue)
}

fun List<Planet>.applyGravity(): List<Planet> = map {
    it.copy(
        velocity = it.velocity + (this - it).fold(Point3D.ORIGIN) { vector, other ->
            vector + Point3D(
                (other.position.x - it.position.x).sign.toLong(),
                (other.position.y - it.position.y).sign.toLong(),
                (other.position.z - it.position.z).sign.toLong(),
            )
        }
    )
}

fun List<Planet>.applyVelocity() = map {
    it.copy(
        position = it.position + it.velocity
    )
}

class Day12(private val numberOfRounds: Int) : AdventDay(2019, 12) {
    override fun part1(input: InputRepresentation): Long {
        val planets = input.map {
            val (x, y, z) = it.toAllLongs().toList()
            Planet(Point3D(x, y, z), Point3D.ORIGIN)
        }
        val simulation = generateSequence(planets) {
            it.applyGravity().applyVelocity()
        }
        return simulation.drop(numberOfRounds).first().sumOf { it.computeEnergy() }
    }

    override fun part2(input: InputRepresentation): Long {
        TODO("Not yet implemented")
    }
}

fun main() = Day12(1000).run()