import java.math.BigInteger
import java.security.MessageDigest

/**
 * Converts string to md5 hash.
 */
@Suppress("unused")
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun gcd(x: Long, y: Long): Long {
    var n1 = x
    var n2 = y

    while (n1 != n2) {
        if (n1 > n2)
            n1 -= n2
        else
            n2 -= n1
    }
    return n1
}
@Suppress("unused")
fun gcd(numbers: Collection<Long>): Long = numbers.reduce(::gcd)

fun lcm(x: Long, y: Long): Long = (x * y)/ gcd(x, y)
fun lcm(numbers: Collection<Long>): Long = numbers.reduce(::lcm)
