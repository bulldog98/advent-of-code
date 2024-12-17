package year2024.day17

sealed interface ComboOperand {
    fun getValue(data: RegistryState): Long

    data object ComboOperand0 : ComboOperand {
        override fun getValue(data: RegistryState): Long = 0
    }

    data object ComboOperand1 : ComboOperand {
        override fun getValue(data: RegistryState): Long = 1
    }

    data object ComboOperand2 : ComboOperand {
        override fun getValue(data: RegistryState): Long = 2
    }

    data object ComboOperand3 : ComboOperand {
        override fun getValue(data: RegistryState): Long = 3
    }

    data object ComboOperand4 : ComboOperand {
        override fun getValue(data: RegistryState): Long = data.registerA
    }

    data object ComboOperand5 : ComboOperand {
        override fun getValue(data: RegistryState): Long = data.registerB
    }

    data object ComboOperand6 : ComboOperand {
        override fun getValue(data: RegistryState): Long = data.registerC
    }

    data object ComboOperand7 : ComboOperand {
        override fun getValue(data: RegistryState): Long = error("reserved op code")
    }

    companion object {
        fun fromOperand(opCode: Long) = when (opCode) {
            0L -> ComboOperand0
            1L -> ComboOperand1
            2L -> ComboOperand2
            3L -> ComboOperand3
            4L -> ComboOperand4
            5L -> ComboOperand5
            6L -> ComboOperand6
            else -> ComboOperand7
        }
    }
}