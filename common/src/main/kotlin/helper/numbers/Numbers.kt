package helper.numbers

val NUMBERS_REGEX = """\d+""".toRegex()

fun String.parseAllInts() =
    NUMBERS_REGEX.findAll(this).map { it.value.toInt() }

fun String.toAllLongs() =
    NUMBERS_REGEX.findAll(this).map { it.value.toLong() }
