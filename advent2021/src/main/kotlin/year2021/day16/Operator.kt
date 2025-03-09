package year2021.day16

data class Operator(
    override val version: Int,
    val lengthTypeID: Int,
    val subPackets: List<Packet>
) : Packet {
    companion object {
        fun parseBinaryString(version: Int, restString: String): Pair<Operator, String> {
            return when (val typeId = restString.first()) {
                '0' -> {
                    val length = restString.drop(1).take(15).toInt(2)
                    val subPacketString = restString.drop(16).take(length)
                    val (subPackets) = generateSequence(emptyList<Packet>() to subPacketString) { (subPackets, restString) ->
                            val (newPacket, rest) = Packet.parseBinaryString(restString)
                            subPackets + newPacket to rest
                    }.first { it.second.isBlank() }
                    Operator(version, 0, subPackets) to restString.drop(16 + length)
                }
                '1' -> {
                    val numberOfSubPackets = restString.drop(1).take(11).toInt(2)
                    val (subPackets, remainder) = generateSequence(emptyList<Packet>() to restString.drop(12)) { (subPackets, restString) ->
                        val (newPacket, rest) = Packet.parseBinaryString(restString)
                        subPackets + newPacket to rest
                    }.take(numberOfSubPackets + 1).last()
                    Operator(version, 1, subPackets) to remainder
                }
                else -> error("invalid typeId: $typeId")
            }
        }
    }
}