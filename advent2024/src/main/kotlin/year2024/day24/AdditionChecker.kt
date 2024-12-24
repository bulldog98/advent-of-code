package year2024.day24

private val cache = mutableMapOf<Int, Set<String>>()
private fun variableNamesFor(bit: Int) = cache.getOrPut(bit) {
    val bitAsTwoDigitString = "$bit".padStart(2, '0')
    setOf("x$bitAsTwoDigitString", "y$bitAsTwoDigitString")
}

private fun ComputationTree.getInvalidPartForHalfAdderSumFor(bit: Int): Pair<String, ComputationTree.(Int) -> Boolean>? = when {
    this !is ComputationTree.XorGateComputation -> variableName to ComputationTree::isHalfAdderSumFor
    this.left !is ComputationTree.InputVariable || left.variableName in variableNamesFor(bit) -> left.variableName to {
        this is ComputationTree.InputVariable && variableName in variableNamesFor(bit)
    }
    this.right !is ComputationTree.InputVariable || right.variableName in variableNamesFor(bit) -> right.variableName to {
        this is ComputationTree.InputVariable && variableName in variableNamesFor(bit)
    }
    else -> null
}

private fun ComputationTree.isHalfAdderSumFor(bit: Int): Boolean =
    this is ComputationTree.XorGateComputation &&
        left is ComputationTree.InputVariable && right is ComputationTree.InputVariable &&
        left.variableName in variableNamesFor(bit) && right.variableName in variableNamesFor(bit)

private fun ComputationTree.getInvalidPartForHalfAdderOverflowFor(bit: Int): Pair<String, ComputationTree.(Int) -> Boolean>? = when {
    this !is ComputationTree.AndGateComputation -> variableName to ComputationTree::isHalfAdderOverflowFor
    this.left !is ComputationTree.InputVariable || left.variableName in variableNamesFor(bit) -> left.variableName to {
        this is ComputationTree.InputVariable && variableName in variableNamesFor(bit)
    }
    this.right !is ComputationTree.InputVariable || right.variableName in variableNamesFor(bit) -> right.variableName to {
        this is ComputationTree.InputVariable && variableName in variableNamesFor(bit)
    }
    else -> null
}

private fun ComputationTree.isHalfAdderOverflowFor(bit: Int): Boolean =
    this is ComputationTree.AndGateComputation &&
        left is ComputationTree.InputVariable && right is ComputationTree.InputVariable &&
        left.variableName in variableNamesFor(bit) && right.variableName in variableNamesFor(bit)

private fun ComputationTree.getInvalidPartForFullAdderSumFor(bit: Int): Pair<String, ComputationTree.(Int) -> Boolean>? = when {
    this !is ComputationTree.XorGateComputation -> variableName to ComputationTree::isFullAdderSumFor
    bit == 1 && left.isHalfAdderOverflowFor(0) -> right.getInvalidPartForHalfAdderSumFor(1)
    bit == 1 && left.isHalfAdderSumFor(1) -> right.getInvalidPartForHalfAdderOverflowFor(0)
    bit == 1 && right.isHalfAdderOverflowFor(0) -> left.getInvalidPartForHalfAdderSumFor(1)
    bit == 1 && right.isHalfAdderSumFor(1) -> left.getInvalidPartForHalfAdderOverflowFor(0)
    bit == 1 -> variableName to ComputationTree::isFullAdderSumFor
    left.isFullAdderOverflow(bit - 1) -> right.getInvalidPartForHalfAdderSumFor(bit)
    left.isHalfAdderSumFor(bit) -> right.getInvalidPartForFullAdderInnerOverflowCarryOverflowFor(bit)
    right.isFullAdderOverflow(bit - 1) -> left.getInvalidPartForHalfAdderSumFor(bit)
    right.isHalfAdderSumFor(bit) -> left.getInvalidPartForFullAdderInnerOverflowCarryOverflowFor(bit)
    else -> variableName to ComputationTree::isFullAdderSumFor
}

private fun ComputationTree.isFullAdderSumFor(bit: Int) = when (bit) {
    1 -> this is ComputationTree.XorGateComputation && (
        (right.isHalfAdderOverflowFor(0) && left.isHalfAdderSumFor(1)) ||
            (left.isHalfAdderOverflowFor(0) && right.isHalfAdderSumFor(1))
        )

    else -> this is ComputationTree.XorGateComputation && (
        (right.isFullAdderOverflow(bit - 1) && left.isHalfAdderSumFor(bit)) ||
            (left.isFullAdderOverflow(bit - 1) && right.isHalfAdderSumFor(bit))
        )
}

private fun ComputationTree.getInvalidPartForFullAdderInnerOverflowCarryOverflowFor(
    bit: Int
): Pair<String, ComputationTree.(Int) -> Boolean>? = when {
    this !is ComputationTree.AndGateComputation -> variableName to ComputationTree::isFullAdderInnerOverflowCarryOverflowFor
    bit == 1 && left.isHalfAdderSumFor(bit) -> right.getInvalidPartForHalfAdderOverflowFor(0)
    bit == 1 && right.isHalfAdderSumFor(bit) -> left.getInvalidPartForHalfAdderOverflowFor(0)
    bit == 1 && left.isHalfAdderOverflowFor(0) -> right.getInvalidPartForHalfAdderSumFor(bit)
    bit == 1 && right.isHalfAdderOverflowFor(0) -> left.getInvalidPartForHalfAdderSumFor(bit)
    bit == 1 -> null
    left.isHalfAdderSumFor(bit) -> right.getInvalidPartForFullAdderOverflowFor(bit - 1)
    right.isHalfAdderSumFor(bit) -> left.getInvalidPartForFullAdderOverflowFor(bit - 1)
    left.isFullAdderOverflow(bit - 1) -> right.getInvalidPartForHalfAdderSumFor(bit)
    right.isFullAdderOverflow(bit - 1) -> left.getInvalidPartForHalfAdderSumFor(bit)
    else -> null
}

private fun ComputationTree.isFullAdderInnerOverflowCarryOverflowFor(bit: Int) = when (bit) {
    1 -> this is ComputationTree.AndGateComputation && (
        (left.isHalfAdderSumFor(bit) && right.isHalfAdderOverflowFor(0)) ||
            (right.isHalfAdderSumFor(bit) && left.isHalfAdderOverflowFor(0))
        )
    else -> this is ComputationTree.AndGateComputation && (
        (left.isHalfAdderSumFor(bit) && right.isFullAdderOverflow(bit - 1)) ||
            (right.isHalfAdderSumFor(bit) && left.isFullAdderOverflow(bit - 1))
        )
}

private fun ComputationTree.getInvalidPartForFullAdderOverflowFor(
    bit: Int
): Pair<String, ComputationTree.(Int) -> Boolean>? = when {
    this !is ComputationTree.OrGateComputation -> variableName to ComputationTree::isFullAdderOverflow
    left.isHalfAdderOverflowFor(bit) -> right.getInvalidPartForFullAdderInnerOverflowCarryOverflowFor(bit)
    right.isHalfAdderOverflowFor(bit) -> left.getInvalidPartForFullAdderInnerOverflowCarryOverflowFor(bit)
    left.isFullAdderInnerOverflowCarryOverflowFor(bit) -> right.getInvalidPartForHalfAdderOverflowFor(bit)
    right.isFullAdderInnerOverflowCarryOverflowFor(bit) -> left.getInvalidPartForHalfAdderOverflowFor(bit)
    else -> null
}

private fun ComputationTree.isFullAdderOverflow(bit: Int): Boolean = this is ComputationTree.OrGateComputation && (
    (left.isHalfAdderOverflowFor(bit) && right.isFullAdderInnerOverflowCarryOverflowFor(bit)) ||
        (right.isHalfAdderOverflowFor(bit) && left.isFullAdderInnerOverflowCarryOverflowFor(bit))
    )

fun ComputationTree.isValidAdditionForBit(bit: Int, maxBit: Int) = when (bit) {
    0 -> isHalfAdderSumFor(0)
    maxBit + 1 -> isFullAdderOverflow(bit - 1)
    else -> isFullAdderSumFor(bit)
}

fun ComputationTree.getInvalidPartForAdditionForBit(
    bit: Int, maxBit: Int
): Pair<String, ComputationTree.(Int) -> Boolean>? = when (bit) {
    0 -> getInvalidPartForHalfAdderSumFor(0)
    maxBit + 1 -> getInvalidPartForFullAdderOverflowFor(bit - 1)
    else -> getInvalidPartForFullAdderSumFor(bit)
}