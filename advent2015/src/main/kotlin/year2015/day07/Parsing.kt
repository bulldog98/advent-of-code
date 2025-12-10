package year2015.day07

import adventday.InputRepresentation

val InputRepresentation.asInstructions: Map<String, Instruction>
    get() = lines
        .associate { Instruction.parse(it) }
