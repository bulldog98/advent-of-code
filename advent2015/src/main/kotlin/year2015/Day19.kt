package year2015

import NotYetImplemented
import adventday.AdventDay
import adventday.InputRepresentation

object Day19 : AdventDay(2015, 19, "Medicine for Rudolph") {
    override fun part1(input: InputRepresentation): Int = input.sections.let { (replacements, inputMoleculeRepresentation) ->
        val inputMolecule = inputMoleculeRepresentation.text
        val replacementRules = replacements.lines.map { it.split(" => ").let { it[0] to it[1] } }
        buildSet {
            replacementRules.forEach { (toReplace, replacement) ->
                toReplace.toRegex().findAll(inputMolecule).forEach { replacementPosition ->
                    add(inputMolecule.replaceRange(replacementPosition.range, replacement))
                }
            }
        }.size
    }

    override fun part2(input: InputRepresentation): Any =
        NotYetImplemented
}

fun main() = Day19.run()