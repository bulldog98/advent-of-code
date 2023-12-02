package year2021.day05

data class Point(val x: Int, val y: Int) {
    constructor(parseString: String) : this(
        parseString.split(',')[0].trim().toInt(),
        parseString.split(',')[1].trim().toInt(),
    )
}
