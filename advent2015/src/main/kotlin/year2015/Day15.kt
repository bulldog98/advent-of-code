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

    typealias IngredientAmount = Pair<Int, Ingredient>
    typealias Recipe = List<IngredientAmount>

    fun InputRepresentation.allRecipes(): List<Recipe> {
        val ingredients = lines.map(Ingredient::parse)
        return partition(100, lines.size).map { it.zip(ingredients) }
    }

    fun partition(number: Int, parts: Int, partitions: List<List<Int>> = listOf(emptyList())): List<List<Int>> =
        when (parts) {
            1 -> partitions.map { it + listOf(number) }
            else -> (0..number).flatMap { numberChosen ->
                partition(number - numberChosen, parts - 1, partitions.map { it + listOf(numberChosen) })
            }
        }

    fun Recipe.scoreSingleAspect(getSingleAspect: (Ingredient) -> Int): Int =
        fold(0) { acc, (amount, ingredient) ->
            acc + amount * getSingleAspect(ingredient)
        }

    fun Recipe.score(): Int =
        listOf<(Ingredient) -> Int>(Ingredient::capacity, Ingredient::durability, Ingredient::flavor, Ingredient::texture)
            .fold(1) { acc, scoreAspect ->
                acc * scoreSingleAspect(getSingleAspect = scoreAspect).coerceAtLeast(0)
            }

    override fun part1(input: InputRepresentation): Int = input
        .allRecipes()
        .maxOf { it.score() }

    override fun part2(input: InputRepresentation): Int = input
        .allRecipes()
        .filter { it.scoreSingleAspect(Ingredient::calories) == 500 }
        .maxOf { it.score() }
}

fun main() = Day15.run()
