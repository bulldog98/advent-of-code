package year2021.day16

sealed interface Packet {
    val version: Int

    companion object {
        fun parseHexString(hexString: String) = parseBinaryString(hexString.windowed(1).joinToString("") {
            it.toInt(16).toString(2).padStart(4, '0')
        }).let { (res, rest) ->
            assert(rest.all { it == '0' }) { "unparsed rest" }
            res
        }

        fun sumOf(packet: Packet, selector: (Packet) -> Int): Int = when (packet) {
            is Literal -> selector(packet)
            is Operator -> selector(packet) + packet.subPackets.sumOf { p -> sumOf(p, selector) }
        }

        fun parseBinaryString(binaryString: String): Pair<Packet, String> {
            val (versionString, typeString) = binaryString.take(6).chunked(3)
            val version = versionString.toInt(2)
            return when (typeString.toInt(2)) {
                Literal.TYPE_ID -> Literal.parseBinaryString(version, binaryString.drop(6))
                else -> Operator.parseBinaryString(version, binaryString.drop(6))
            }
        }
    }
}