package year2024.day17

private typealias ComboOperandFunction = (registry: RegistryState) -> Long

sealed interface ComboOperand : (RegistryState) -> Long {

    data class ConstantComboOperand(val const: Long) : ComboOperand {
        override fun invoke(p1: RegistryState): Long = const
    }

    data class RegistryComboOperand(val getRegistryValue: ComboOperandFunction): ComboOperand {
        override fun invoke(registry: RegistryState): Long = getRegistryValue(registry)
    }

    data object InvalidComboOperand : ComboOperand {
        override fun invoke(data: RegistryState): Long = error("reserved op code")
    }

    companion object {
        private val lookupTable = mutableMapOf<Long, ComboOperand>()

        fun fromOperand(opCode: Long) = lookupTable.getOrPut(opCode) {
            when (opCode) {
                in 0..3L -> ConstantComboOperand(opCode)
                4L -> RegistryComboOperand(RegistryState::registerA)
                5L -> RegistryComboOperand(RegistryState::registerB)
                6L -> RegistryComboOperand(RegistryState::registerC)
                else -> InvalidComboOperand
            }
        }
    }
}