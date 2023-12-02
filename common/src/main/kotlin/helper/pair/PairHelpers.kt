package helper.pair

fun <T, R, F> Pair<T, R>.mapFirst(mapping: (T) -> F): Pair<F, R> =
    mapping(first) to second

fun <T, R, F> Pair<T, R>.mapSecond(mapping: (R) -> F): Pair<T, F> =
    first to mapping(second)
