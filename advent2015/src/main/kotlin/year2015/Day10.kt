package year2015

import adventday.AdventDay
import adventday.InputRepresentation

object Day10 : AdventDay(2015, 10, "Elves Look, Elves Say") {
    // took the lookup from https://en.wikipedia.org/wiki/Look-and-say_sequence
    val elements = mapOf(
        "H"  to "22",
        "He" to "13112221133211322112211213322112",
        "Li" to "312211322212221121123222112",
        "Be" to "111312211312113221133211322112211213322112",
        "B"  to "1321132122211322212221121123222112",
        "C"  to "3113112211322112211213322112",
        "N"  to "111312212221121123222112",
        "O"  to "132112211213322112",
        "F"  to "31121123222112",
        "Ne" to "111213322112",
        "Na" to "123222112",
        "Mg" to "3113322112",
        "Al" to "1113222112",
        "Si" to "1322112",
        "P"  to "311311222112",
        "S"  to "1113122112",
        "Cl" to "132112",
        "Ar" to "3112",
        "K"  to "1112",
        "Ca" to "12",
        "Sc" to "3113112221133112",
        "Ti" to "11131221131112",
        "V"  to "13211312",
        "Cr" to "31132",
        "Mn" to "111311222112",
        "Fe" to	"13122112",
        "Co" to "32112",
        "Ni" to "11133112",
        "Cu" to "131112",
        "Zn" to "312",
        "Ga" to "13221133122211332",
        "Ge" to "31131122211311122113222",
        "As" to "11131221131211322113322112",
        "Se" to "13211321222113222112",
        "Br" to "3113112211322112",
        "Kr" to "11131221222112",
        "Rb" to "1321122112",
        "Sr" to "3112112",
        "Y"  to "1112133",
        "Zr" to "12322211331222113112211",
        "Nb" to "1113122113322113111221131221",
        "Mo" to "13211322211312113211",
        "Tc" to "311322113212221",
        "Ru" to "132211331222113112211",
        "Rh" to "311311222113111221131221",
        "Pd" to "111312211312113211",
        "Ag" to "132113212221",
        "Cd" to "3113112211",
        "In" to "11131221",
        "Sn" to "13211",
        "Sb" to "3112221",
        "Te" to "1322113312211",
        "I"  to "311311222113111221",
        "Xe" to "11131221131211",
        "Cs" to "13211321",
        "Ba" to "311311",
        "La" to "11131",
        "Ce" to "1321133112",
        "Pr" to "31131112",
        "Nd" to "111312",
        "Pm" to "132",
        "Sm" to "311332",
        "Eu" to "1113222",
        "Gd" to "13221133112",
        "Tb" to "3113112221131112",
        "Dy" to "111312211312",
        "Ho" to "1321132",
        "Er" to "311311222",
        "Tm" to "11131221133112",
        "Yb" to "1321131112",
        "Lu" to "311312",
        "Hf" to "11132",
        "Ta" to	"13112221133211322112211213322113",
        "W"  to "312211322212221121123222113",
        "Re" to "111312211312113221133211322112211213322113",
        "Os" to "1321132122211322212221121123222113",
        "Ir" to "3113112211322112211213322113",
        "Pt" to "111312212221121123222113",
        "Au" to "132112211213322113",
        "Hg" to "31121123222113",
        "Tl" to "111213322113",
        "Pb" to "123222113",
        "Bi" to "3113322113",
        "Po" to "1113222113",
        "At" to "1322113",
        "Rn" to "311311222113",
        "Fr" to "1113122113",
        "Ra" to "132113",
        "Ac" to "3113",
        "Th" to "1113",
        "Pa" to "13",
        "U"  to "3",
    )
    val decayMap = mapOf(
        "H" to "H".split("."),
        "He" to "Hf.Pa.H.Ca.Li".split("."),
        "Li" to "He".split("."),
        "Be" to "Ge.Ca.Li".split("."),
        "B" to "Be".split("."),
        "C" to "B".split("."),
        "N" to "C".split("."),
        "O" to "N".split("."),
        "F" to "O".split("."),
        "Ne" to "F".split("."),
        "Na" to "Ne".split("."),
        "Mg" to "Pm.Na".split("."),
        "Al" to "Mg".split("."),
        "Si" to "Al".split("."),
        "P" to "Ho.Si".split("."),
        "S" to "P".split("."),
        "Cl" to "S".split("."),
        "Ar" to "Cl".split("."),
        "K" to "Ar".split("."),
        "Ca" to "K".split("."),
        "Sc" to "Ho.Pa.H.Ca.Co".split("."),
        "Ti" to "Sc".split("."),
        "V" to "Ti".split("."),
        "Cr" to "V".split("."),
        "Mn" to "Cr.Si".split("."),
        "Fe" to "Mn".split("."),
        "Co" to "Fe".split("."),
        "Ni" to "Zn.Co".split("."),
        "Cu" to "Ni".split("."),
        "Zn" to "Cu".split("."),
        "Ga" to "Eu.Ca.Ac.H.Ca.Zn".split("."),
        "Ge" to "Ho.Ga".split("."),
        "As" to "Ge.Na".split("."),
        "Se" to "As".split("."),
        "Br" to "Se".split("."),
        "Kr" to "Br".split("."),
        "Rb" to "Kr".split("."),
        "Sr" to "Rb".split("."),
        "Y" to "Sr.U".split("."),
        "Zr" to "Y.H.Ca.Tc".split("."),
        "Nb" to "Er.Zr".split("."),
        "Mo" to "Nb".split("."),
        "Tc" to "Mo".split("."),
        "Ru" to "Eu.Ca.Tc".split("."),
        "Rh" to "Ho.Ru".split("."),
        "Pd" to "Rh".split("."),
        "Ag" to "Pd".split("."),
        "Cd" to "Ag".split("."),
        "In" to "Cd".split("."),
        "Sn" to "In".split("."),
        "Sb" to "Pm.Sn".split("."),
        "Te" to "Eu.Ca.Sb".split("."),
        "I" to "Ho.Te".split("."),
        "Xe" to "I".split("."),
        "Cs" to "Xe".split("."),
        "Ba" to "Cs".split("."),
        "La" to "Ba".split("."),
        "Ce" to "La.H.Ca.Co".split("."),
        "Pr" to "Ce".split("."),
        "Nd" to "Pr".split("."),
        "Pm" to "Nd".split("."),
        "Sm" to "Pm.Ca.Zn".split("."),
        "Eu" to "Sm".split("."),
        "Gd" to "Eu.Ca.Co".split("."),
        "Tb" to "Ho.Gd".split("."),
        "Dy" to "Tb".split("."),
        "Ho" to "Dy".split("."),
        "Er" to "Ho.Pm".split("."),
        "Tm" to "Er.Ca.Co".split("."),
        "Yb" to "Tm".split("."),
        "Lu" to "Yb".split("."),
        "Hf" to "Lu".split("."),
        "Ta" to "Hf.Pa.H.Ca.W".split("."),
        "W" to "Ta".split("."),
        "Re" to "Ge.Ca.W".split("."),
        "Os" to "Re".split("."),
        "Ir" to "Os".split("."),
        "Pt" to "Ir".split("."),
        "Au" to "Pt".split("."),
        "Hg" to "Au".split("."),
        "Tl" to "Hg".split("."),
        "Pb" to "Tl".split("."),
        "Bi" to "Pm.Pb".split("."),
        "Po" to "Bi".split("."),
        "At" to "Po".split("."),
        "Rn" to "Ho.At".split("."),
        "Fr" to "Rn".split("."),
        "Ra" to "Fr".split("."),
        "Ac" to "Ra".split("."),
        "Th" to "Ac".split("."),
        "Pa" to "Th".split("."),
        "U" to "Pa".split("."),
    )

    fun String.splitToElements(): List<String> = when {
        isEmpty() -> emptyList()
        else -> {
            val firstElement = elements
                .filterValues {
                    startsWith(it)
                }
                .maxBy { it.value.length }
            listOf(firstElement.key) + (this.substring(firstElement.value.length)).splitToElements()
        }
    }

    fun String.readSequenceWithOutLookup(): String = toParts().joinToString("") {
        "${it.length}${it.first()}"
    }

    tailrec fun String.toParts(prefix: List<String> = emptyList()): List<String> = when {
        isEmpty() -> prefix
        else -> {
            val start = first()
            val nextPart = this.takeWhile { it == start }
            dropWhile { it == start }.toParts(prefix + nextPart)
        }
    }

    override fun part1(input: InputRepresentation): Int =
        generateSequence(input.lines[0].splitToElements()) {
            it.flatMap { element -> decayMap[element]!! }
        }.drop(1)
            .take(40)
            .last()
            .sumOf {
                elements[it]!!.length
            }

    override fun part2(input: InputRepresentation): Int =
        generateSequence(input.lines[0].splitToElements()) {
            it.flatMap { element -> decayMap[element]!! }
        }.drop(1)
            .take(50)
            .last()
            .sumOf {
                elements[it]!!.length
            }
}

fun main() = Day10.run()
