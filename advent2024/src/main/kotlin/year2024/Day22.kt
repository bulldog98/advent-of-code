package year2024

import adventday.AdventDay
import adventday.InputRepresentation
import helper.numbers.toAllLongs
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

object Day22 : AdventDay(2024, 22) {
    private fun Long.generateSecrets() = generateSequence(this) { oldSecret ->
        val firstStep = ((oldSecret * 64) xor oldSecret) % 16777216
        val secondStep = ((firstStep / 32) xor firstStep) % 16777216
        ((secondStep * 2048) xor secondStep) % 16777216
    }

    private fun Long.generateSecretPrices() = generateSecrets().map {
        it % 10
    }

    private fun Long.generatePriceChanges() = generateSecretPrices().zipWithNext().map { (a, b) ->
        b - a
    }

    override fun part1(input: InputRepresentation): Long = input.sumOf {
        val initialSecret = it.toAllLongs().first()
        initialSecret.generateSecrets().take(2001).last()
    }

    // pretty slow since it's not properly parallelized
    override fun part2(input: InputRepresentation): Long {
        val numbers = input.map { it.toAllLongs().first() }
        val prices = numbers.map { it.generateSecretPrices().drop(1).take(2000).toList() }
        val priceChanges = numbers.map { it.generatePriceChanges().take(2000).windowed(4).toList() }
        val allOccurringChanges = priceChanges.flatten().distinct()
        println("start splitting")
        return runBlocking {
            val asyncActions = allOccurringChanges.chunked(1000).map {
                async {
                    val result = it.maximalBananaPrice(priceChanges, prices)
                    println("one block done")
                    result
                }
            }
            asyncActions.maxOf {
                it.await()
            }
        }
    }

    private fun List<List<Long>>.maximalBananaPrice(
        priceChanges: List<List<List<Long>>>,
        prices: List<List<Long>>
    ) = maxOf { sequence ->
        val indexes = priceChanges.map {
            it.indexOf(sequence) + 3
        }
        indexes.zip(prices).sumOf { (i, prices) ->
            // not found is -1 but +3 == 2
            if (i == 2) 0L else prices[i]
        }
    }
}

fun main() = Day22.run()