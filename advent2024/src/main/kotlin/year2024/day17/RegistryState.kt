package year2024.day17

import helper.numbers.toAllLongs

data class RegistryState(
    val registerA: Long,
    val registerB: Long,
    val registerC: Long,
) {
    companion object {
        fun parse(input: List<String>): RegistryState {
            assert(input.size == 3)
            return RegistryState(
                input[0].toAllLongs().first(),
                input[1].toAllLongs().first(),
                input[2].toAllLongs().first(),
            )
        }
    }
}