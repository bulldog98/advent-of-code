package year2021.day16

data class Literal(override val version: Int, override val value: Long) : Packet {
    companion object {
        const val TYPE_ID = 4

        fun parseBinaryString(version: Int, restString: String): Pair<Literal, String> {
            val chunks = restString.chunked(5)
            val continueChunks = chunks.takeWhile { it.first() == '1' }
            val contentChunks = continueChunks + chunks.drop(continueChunks.size).first()
            return Literal(version, contentChunks.joinToString("") { it.drop(1) }.toLong(2)) to
                restString.drop(5 * contentChunks.size)
        }
    }
}