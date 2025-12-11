package year2015

import adventday.AdventDay
import adventday.InputRepresentation
import helper.numbers.toAllLongs

object Day15 : AdventDay(2015, 15, "Science for Hungry People") {
    data class Ingredient(
        val name: String,
        val capacity: Int,
        val durability: Int,
        val flavor: Int,
        val texture: Int,
        val calories: Int,
    ) {
        companion object {
            fun parse(line: String): Ingredient {
                val (capacity, durability, flavor, texture, calories) = line.toAllLongs().map { it.toInt() }.toList()
                return Ingredient(
                    line.split(": ")[0],
                    capacity,
                    durability,
                    flavor,
                    texture,
                    calories,
                )
            }
        }
    }

    fun partition(number: Int, parts: Int, partitions: List<List<Int>> = listOf(emptyList())): List<List<Int>> =
        when (parts) {
            1 -> partitions.map { it + listOf(number) }
            else -> (0..number).flatMap { numberChosen ->
                partition(number - numberChosen, parts - 1, partitions.map { it + listOf(numberChosen) })
            }
        }

    override fun part1(input: InputRepresentation): Long = input
        .lines
        .map(Ingredient::parse)
        .let { ingredients ->
            partition(100, ingredients.size).maxOf { parts ->
                val list = parts.zip(ingredients)
                val capacity = list.sumOf { (amount, ingredient) -> amount * ingredient.capacity }.coerceAtLeast(0)
                val durability = list.sumOf { (amount, ingredient) -> amount * ingredient.durability }.coerceAtLeast(0)
                val flavor = list.sumOf { (amount, ingredient) -> amount * ingredient.flavor }.coerceAtLeast(0)
                val texture = list.sumOf { (amount, ingredient) -> amount * ingredient.texture }.coerceAtLeast(0)
                capacity.toLong() * durability * flavor * texture
            }
        }

    override fun part2(input: InputRepresentation): Long = input
        .lines
        .map(Ingredient::parse)
        .let { ingredients ->
            partition(100, ingredients.size)
                .map { it.zip(ingredients) }
                .filter {
                    it.sumOf { (amount, ingredient) -> amount * ingredient.calories } == 500
                }
                .maxOf { list ->
                    val capacity = list.sumOf { (amount, ingredient) -> amount * ingredient.capacity }.coerceAtLeast(0)
                    val durability =
                        list.sumOf { (amount, ingredient) -> amount * ingredient.durability }.coerceAtLeast(0)
                    val flavor = list.sumOf { (amount, ingredient) -> amount * ingredient.flavor }.coerceAtLeast(0)
                    val texture = list.sumOf { (amount, ingredient) -> amount * ingredient.texture }.coerceAtLeast(0)
                    capacity.toLong() * durability * flavor * texture
                }
        }
}

fun main() = Day15.run()
